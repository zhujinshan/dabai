package com.dabai.proxy.encypt.core;


import com.dabai.proxy.encypt.annontation.AESDecryptParam;
import com.dabai.proxy.encypt.annontation.DecryptApi;
import com.dabai.proxy.encypt.utils.AesEncryptUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 13:47
 */
@Aspect
public class DecyptInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.dabai.proxy.encypt.annontation.DecryptApi)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object dataInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        DecryptApi decryptApi = method.getAnnotation(DecryptApi.class);
        String currentAppSecret = SignatureContext.getCurrentSecret();
        String currentAppId = SignatureContext.getCurrentAppId();
        if (StringUtils.isEmpty(currentAppSecret) || StringUtils.isEmpty(currentAppId)) {
            logger.error("appSecret or appId not config, please check config");
            return joinPoint.proceed();
        }

        String[] aesAppIds = decryptApi.aesAppId();
        if (aesAppIds.length > 0 && !ArrayUtils.contains(aesAppIds, currentAppId)) {
            logger.info("currentAppId not in @DecryptApi.decryptAESAppId. skip aes decrypt. currentAppId={}, decryptAESAppIds={}", currentAppId, aesAppIds);
            return joinPoint.proceed();
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }

            if (!isSimpleType(arg.getClass())) {
                AESDecryptDataResolver.resolverData(arg, currentAppSecret);
            } else {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                for (Annotation annotation : parameterAnnotation) {
                    AESDecryptParam aesDecryptParam = AnnotationUtils.getAnnotation(annotation, AESDecryptParam.class);
                    if (aesDecryptParam != null) {
                        try {
                            String decrypValue = AesEncryptUtils.aesDecrypt(arg.toString(), currentAppSecret);
                            args[i] = decrypValue;
                        } catch (Exception e) {
                            logger.error("aes解密异常, 解密对象值：" + arg, e);
                        }
                    }
                }
            }
        }

        SignatureContext.clearCurrentAppInfo();
        return joinPoint.proceed(args);
    }

    private boolean isSimpleType(Class<?> type) {
        return BeanUtils.isSimpleProperty(type) || type == String.class;
    }

}
