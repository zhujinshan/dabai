package com.dabai.proxy.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author zhujinshan
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.setSerializationInclusion(Include.NON_NULL);
        JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.IGNORE_UNDEFINED, true);

    }

    public static String toJson(Object obj) {
        try {
            return JSON.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json error", e);
        }
        return StringUtils.EMPTY;
    }

    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return JSON.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("json error", e);
            return null;
        }
    }

    public static Map<String, Object> toMap(Object obj) {
        return JSON.convertValue(obj, Map.class);
    }
}
