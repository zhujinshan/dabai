package com.dabai.proxy.encypt.core;

import com.dabai.proxy.encypt.config.DecryptConfig;
import com.dabai.proxy.encypt.config.DecryptListConfig;
import com.dabai.proxy.encypt.exception.ApiSignCheckException;
import com.dabai.proxy.encypt.utils.SignatureVerifyUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 14:20
 */
public class SignatureVerifyFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DecryptListConfig decryptListConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SignatureVerifyFilter(DecryptListConfig decryptListConfig) {
        this.decryptListConfig = decryptListConfig;
        this.objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                .configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true)
                .configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String uri = req.getRequestURI();
        logger.debug("RequestURI: {}", uri);

        SignatureContext.clearCurrentAppInfo();

        try {
            if (SignatureContext.isNeedCheck(uri, req.getMethod())) {
                //DecryptRequestWrapper requestWrapper = new DecryptRequestWrapper(req);
                SortedMap<String, String> parameterMap = getParameterMapFromQuery(req.getQueryString());
                String appid = parameterMap.get(DecryptConstant.APP_ID);
                if (StringUtils.isNotEmpty(appid)) {
                    DecryptConfig decryptConfig = SignatureContext.DECRYPT_CONFIG_MAP.get(appid);
                    if (decryptConfig != null && StringUtils.isNotEmpty(decryptConfig.getAppsecret())) {
                        SignatureContext.setAppIdSecret(appid, decryptConfig.getAppsecret());
                    }
                }

                // 调试模式不加解密
                if (decryptListConfig.getDebug()) {
                    chain.doFilter(req, resp);
                    return;
                }

                SignatureVerifyUtils.signatureVerify(parameterMap);
                chain.doFilter(req, resp);
            } else {
                chain.doFilter(req, resp);
            }
        } catch (ApiSignCheckException e) {
            logger.info("ApiSignCheckException: {}", e.getMessage());
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writer = resp.getWriter();
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("code", "-1");
            resultMap.put("message", e.getMessage());
            writer.write(objectMapper.writeValueAsString(resultMap));
            writer.flush();
            writer.close();
        } finally {
            SignatureContext.clearCurrentAppInfo();
        }
    }

    private SortedMap<String, String> getParameterMapFromQuery(String queryString) {
        SortedMap<String, String> params = Maps.newTreeMap();
        if (StringUtils.isNotEmpty(queryString)) {
            String[] split = StringUtils.split(queryString, "&");
            if (split != null && split.length > 0) {
                for (String s : split) {
                    String[] split1 = StringUtils.split(s, "=");
                    if (split1 != null && split1.length > 1) {
                        params.put(split1[0], split1[1]);
                    }
                }
            }
        }
        return params;
    }

}
