package com.dabai.proxy.encypt.utils;

import com.dabai.proxy.encypt.config.DecryptConfig;
import com.dabai.proxy.encypt.core.DecryptConstant;
import com.dabai.proxy.encypt.core.SignatureContext;
import com.dabai.proxy.encypt.exception.ApiSignCheckException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 15:51
 */
public class SignatureVerifyUtils {

    private static final String SIGN_NOT_MATCH_MESSAGE = "sign verification failed. [requestSign=%s, correctSign=%s, md5String=%s]";

    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureVerifyUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void signatureVerify(SortedMap<String, String> parameterMap) {
        LOGGER.info("signatureVerify, parameterMap={}", jsonString(parameterMap));

        String sign = parameterMap.get(DecryptConstant.SIGN_KEY);
        if (sign == null) {
            throw new ApiSignCheckException("less sign param");
        }

        if (!parameterMap.containsKey(DecryptConstant.TIMESTAMP_KEY)) {
            throw new ApiSignCheckException("less timestamp param");
        }

        String appid = parameterMap.get(DecryptConstant.APP_ID);
        if (appid == null) {
            throw new ApiSignCheckException("less appid param");
        }

        String timestamp = parameterMap.get(DecryptConstant.TIMESTAMP_KEY);
        if (!StringUtils.isNumeric(timestamp)) {
            throw new ApiSignCheckException("timestamp wrong format");
        }

        DecryptConfig decryptConfig = SignatureContext.DECRYPT_CONFIG_MAP.get(appid);
        if (decryptConfig == null) {
            throw new ApiSignCheckException("invalid appid");
        }

        String appsecret = decryptConfig.getAppsecret();
        if (StringUtils.isEmpty(appsecret)) {
            throw new ApiSignCheckException("invalid appsecret, please check config. appid = '" + appid + "'");
        }

        long paramTime = Long.parseLong(timestamp);
        if (decryptConfig.getCheckTime()) {
            long expiredTime = decryptConfig.getEffectivePeriod() * 1000;
            if (System.currentTimeMillis() - paramTime > expiredTime) {
                throw new ApiSignCheckException("timestamp unavailable");
            }
        }

        String paramString = paramString(parameterMap, appsecret);
        LOGGER.info("signatureVerify, before md5, md5String={}", paramString);
        String digestAsHex = DigestUtils.md5DigestAsHex(paramString.getBytes());
        LOGGER.info("signatureVerify, after md5, md5={}", digestAsHex);
        if (!digestAsHex.equals(sign)) {
            String errorMsg = String.format(SIGN_NOT_MATCH_MESSAGE, sign, digestAsHex, paramString);
            LOGGER.error(errorMsg);
            throw new ApiSignCheckException("sign verification failed.");
        }
    }


    private static String paramString(SortedMap<String, String> paramMap, String appSecret) {
        StringBuilder paramString = new StringBuilder();
        for (String s : paramMap.keySet()) {
            if (!s.equals(DecryptConstant.SIGN_KEY)) {
                paramString.append(s).append(paramMap.get(s));
            }
        }
        paramString.append(appSecret);
        return paramString.toString();
    }

    private static String jsonString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            LOGGER.error("json process exception", e);
        }
        return "";
    }

    public static void main(String[] args) {
        SortedMap<String, String> data = Maps.newTreeMap();
        //data.put("name", "FTKt5s+YR32L/KClMz/GYQ==");
        data.put("appid", "HBX");
        long time = System.currentTimeMillis();
        data.put("timestamp", time + "");
        System.out.println(time);
        String digestAsHex = DigestUtils.md5DigestAsHex(paramString(data, "4ad7b457319c8e870a73ca251eacd721").getBytes());
        System.out.println(digestAsHex);

    }
}
