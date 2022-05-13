package com.dabai.proxy.httpclient.huanong;

import com.dabai.proxy.helper.EnvironmentHelper;
import com.dabai.proxy.httpclient.api.HttpRequest;
import com.dabai.proxy.httpclient.api.RequestProcessor;
import com.dabai.proxy.httpclient.huanong.param.HuanongCommonParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 华农签名处理
 * @author: jinshan.zhu
 * @date: 2022/5/8 14:40
 */
@Slf4j
public class HuanongHttpClientProcessor implements RequestProcessor {

    private static final String APPID = "http.huanong.appid";

    private static final String APP_KEY = "http.huanong.appkey";

    @Override
    public void process(HttpRequest request) throws Exception {
        String appId = EnvironmentHelper.environment.getProperty(APPID, String.class);
        String appKey = EnvironmentHelper.environment.getProperty(APP_KEY, String.class);
        Long timestamp = System.currentTimeMillis();
        String nonceStr = RandomStringUtils.randomAlphanumeric(30);
        HuanongCommonParam param = (HuanongCommonParam) request.getEntity();
        param.setTimestamp(timestamp.toString());
        param.setAppid(appId);
        param.setNonceStr(nonceStr);
        param.setSign(getSign(appKey, nonceStr, String.valueOf(timestamp)));
        request.setEntity(param);
    }

    private String getSign(String appkey, String nonceStr, String timestamp) {
        String signVal = appkey + nonceStr + timestamp;
        return DigestUtils.md5DigestAsHex(signVal.getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

}
