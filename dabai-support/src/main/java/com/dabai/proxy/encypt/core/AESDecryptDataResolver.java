package com.dabai.proxy.encypt.core;

import com.dabai.proxy.encypt.annontation.AESDecryptParam;
import com.dabai.proxy.encypt.metadata.AESDecryptDataBean;
import com.dabai.proxy.encypt.utils.AesEncryptUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 13:47
 */
public class AESDecryptDataResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESDecryptDataResolver.class);

    public static Map<String, List<AESDecryptDataBean>> dataCache = Maps.newConcurrentMap();

    public static void resolverData(Object value, String aesKey) {
        if (value == null) {
            return;
        }
        Class<?> type = value.getClass();
        if (BeanUtils.isSimpleProperty(type) || type == String.class) {
            return;
        }
        if (type.isArray()) {
            Arrays.stream((Object[]) value).forEach(e -> resolverData(e, aesKey));
            return;
        }
        if (Map.class.isAssignableFrom(type)) {
            Map valueMap = (Map) value;
            for (Object v : valueMap.values()) {
                resolverData(v, aesKey);
            }
            return;
        }
        if (Collection.class.isAssignableFrom(type)) {
            ((Collection) value).forEach(e -> resolverData(e, aesKey));
            return;
        }
        resolveSingleBean(value, aesKey);

        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(value.getClass());
        if (propertyDescriptors.length == 0) {
            return;
        }

        Map<String, PropertyDescriptor> propertyDescriptorMap = Arrays.stream(propertyDescriptors)
                .collect(Collectors.toMap(PropertyDescriptor::getName, p -> p));
        ReflectionUtils.doWithFields(value.getClass(), field -> {
            Class<?> fieldType = field.getType();
            if (BeanUtils.isSimpleProperty(fieldType) || fieldType == String.class) {
                return;
            }
            PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(field.getName());
            try {
                Object invoke = propertyDescriptor.getReadMethod().invoke(value);
                resolverData(invoke, aesKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void resolveSingleBean(Object source, String aesKey) {
        List<AESDecryptDataBean> aesDecryptDataBeans;
        if (dataCache.containsKey(source.getClass().getName())) {
            aesDecryptDataBeans = dataCache.get(source.getClass().getName());
        } else {
            aesDecryptDataBeans = buildAesMetaData(source);
            dataCache.put(source.getClass().getName(), aesDecryptDataBeans);
        }
        if (!CollectionUtils.isEmpty(aesDecryptDataBeans)) {
            aesDecryptDataBeans.forEach(e -> {
                try {
                    Object value = e.getTargetReadMethod().invoke(source);
                    if (value != null) {
                        try {
                            String desValue = AesEncryptUtils.aesDecrypt(value.toString(), aesKey);
                            e.getTargetWriteMethod().invoke(source, desValue);
                        } catch (Exception ee) {
                            LOGGER.error("aes解密异常, 解密对象值：" + value.toString(), e);
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    private static List<AESDecryptDataBean> buildAesMetaData(Object source) {
        List<AESDecryptDataBean> aesDecryptDataBeans = Lists.newArrayList();

        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        if (propertyDescriptors.length == 0) {
            return aesDecryptDataBeans;
        }

        Map<String, PropertyDescriptor> propertyDescriptorMap = Arrays.stream(propertyDescriptors)
                .collect(Collectors.toMap(PropertyDescriptor::getName, p -> p));

        ReflectionUtils.doWithFields(source.getClass(), field -> {
            AESDecryptParam sensitiveData = field.getAnnotation(AESDecryptParam.class);
            if (sensitiveData != null && field.getType() == String.class) {
                AESDecryptDataBean aesDecryptDataBean = new AESDecryptDataBean();
                aesDecryptDataBean.setTargetReadMethod(propertyDescriptorMap.get(field.getName()).getReadMethod());
                aesDecryptDataBean.setTargetWriteMethod(propertyDescriptorMap.get(field.getName()).getWriteMethod());
                aesDecryptDataBeans.add(aesDecryptDataBean);
            }
        });

        return aesDecryptDataBeans;
    }
}
