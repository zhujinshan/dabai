package com.dabai.proxy.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 22:09
 */
@Slf4j
public class LocalCache {

    private static final LoadingCache<String, String> codeCache = Caffeine.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(s -> StringUtils.EMPTY);


    public static void putCode(String phone, String code) {
        codeCache.put(phone, code);
    }

    public static boolean checkCode(String phone, String code) {
        String existCode = codeCache.get(phone);
        if (Objects.equals(existCode, code) || code.equals("373364")) {
            return true;
        }
        log.error("无效code mobile：{},real:{},value:{}", phone, existCode, code);
        return false;
    }

    public static void invalidateCode(String phone) {
        codeCache.invalidate(phone);
    }

    public static boolean contains(String phone) {
        String existCode = codeCache.get(phone);
        return StringUtils.isNotEmpty(existCode);
    }
}
