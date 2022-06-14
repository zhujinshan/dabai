package com.dabai.proxy.encypt.core;

import com.dabai.proxy.encypt.annontation.DecryptApi;
import com.dabai.proxy.encypt.config.DecryptConfig;
import com.dabai.proxy.encypt.config.DecryptListConfig;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 16:35
 */
public class SignatureContext implements ApplicationContextAware, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    private static final Set<String> SIGN_URL = Sets.newConcurrentHashSet();

    public static final Map<String, DecryptConfig> DECRYPT_CONFIG_MAP = Maps.newConcurrentMap();

    private static final ThreadLocal<MutablePair<String, String>> APP_KEY_SECRET_CONTEXT = new ThreadLocal<>();

    private String contextPath;

    private static final String FILE_SEPRATE = "/";

    private static final String METHOD_SEPRATE = ":";

    public SignatureContext(DecryptListConfig decryptListConfig) {
        List<DecryptConfig> list = decryptListConfig.getList();
        for (DecryptConfig decryptConfig : list) {
            DECRYPT_CONFIG_MAP.put(decryptConfig.getAppid(), decryptConfig);
        }
    }

    public static boolean isNeedCheck(String uri, String methodType) {
        String prefixUri = methodType.toUpperCase() + METHOD_SEPRATE + uri;
        return SIGN_URL.contains(prefixUri);
    }


    public static void setAppIdSecret(String appId, String appSecret) {
        MutablePair<String, String> appInfo = new MutablePair<>(appId, appSecret);
        APP_KEY_SECRET_CONTEXT.set(appInfo);
    }

    public static String getCurrentSecret() {
        MutablePair<String, String> pair = APP_KEY_SECRET_CONTEXT.get();
        if (pair != null) {
            return pair.right;
        }
        return null;
    }

    public static String getCurrentAppId() {
        MutablePair<String, String> pair = APP_KEY_SECRET_CONTEXT.get();
        if (pair != null) {
            return pair.left;
        }
        return null;
    }

    public static void clearCurrentAppInfo() {
        APP_KEY_SECRET_CONTEXT.remove();
    }

    /*@Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            this.contextPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");

            Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
            initData(beanMap);
        }
    }*/

    private void initData(Map<String, Object> beanMap) {
        if (beanMap != null) {
            for (Object bean : beanMap.values()) {
                Class<?> clz = bean.getClass();
                Method[] methods = clz.getMethods();
                for (Method method : methods) {
                    DecryptApi decrypt = AnnotationUtils.findAnnotation(method, DecryptApi.class);
                    if (decrypt != null) {
                        String uri = getApiUri(clz, method);
                        logger.debug("Decrypt URI: {}", uri);
                        SIGN_URL.add(uri);
                    }
                }
            }
        }
    }

    private String getApiUri(Class<?> clz, Method method) {
        String methodType = null;
        StringBuilder uri = new StringBuilder();

        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (reqMapping != null) {
            uri.append(formatUri(reqMapping.value()[0]));
        }

        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);

        if (getMapping != null) {
            methodType = HttpMethod.GET.name();
            uri.append(formatUri(getMapping.value()[0]));
        } else if (postMapping != null) {
            methodType = HttpMethod.POST.name();
            uri.append(formatUri(postMapping.value()[0]));
        } else if (putMapping != null) {
            methodType = HttpMethod.PUT.name();
            uri.append(formatUri(putMapping.value()[0]));
        } else if (deleteMapping != null) {
            methodType = HttpMethod.DELETE.name();
            uri.append(formatUri(deleteMapping.value()[0]));
        } else if (requestMapping != null) {
            RequestMethod requestMethod = RequestMethod.GET;
            if (requestMapping.method().length > 0) {
                requestMethod = requestMapping.method()[0];
            }
            methodType = requestMethod.name();
            uri.append(formatUri(requestMapping.value()[0]));
        }

        if (StringUtils.hasText(this.contextPath) && !FILE_SEPRATE.equals(this.contextPath)) {
            if (this.contextPath.endsWith(FILE_SEPRATE)) {
                this.contextPath = this.contextPath.substring(0, this.contextPath.length() - 1);
            }
            return methodType + METHOD_SEPRATE + contextPath + uri.toString();
        }

        return methodType + METHOD_SEPRATE + uri.toString();
    }

    private String formatUri(String uri) {
        if (FILE_SEPRATE.equals(uri)) {
            return "";
        }

        String formarUri = uri;
        if (!uri.startsWith(FILE_SEPRATE)) {
            formarUri = FILE_SEPRATE + uri;
        }

        if (formarUri.endsWith(FILE_SEPRATE)) {
            formarUri = formarUri.substring(0, formarUri.length() - 1);
        }

        return formarUri;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.contextPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        initData(beanMap);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
