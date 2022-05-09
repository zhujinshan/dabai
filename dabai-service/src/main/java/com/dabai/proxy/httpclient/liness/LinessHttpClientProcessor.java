package com.dabai.proxy.httpclient.liness;

import com.dabai.proxy.helper.EnvironmentHelper;
import com.dabai.proxy.httpclient.api.HttpRequest;
import com.dabai.proxy.httpclient.api.RequestProcessor;
import com.dabai.proxy.httpclient.liness.param.LinessBaseParam;
import com.dabai.proxy.utils.JsonUtils;
import com.dabai.proxy.utils.TimeUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/8 14:40
 */
@Slf4j
public class LinessHttpClientProcessor implements RequestProcessor {

    private static final String MERCHANT_ID = "http.liness.merchantId";

    private static final String PARTNER_ID = "http.liness.partnerId";

    private static final String RAS_KEY = "http.liness.rasKey";

    private static final String SIGN_TYPE = "RSA";

    @Override
    public void process(HttpRequest request) throws Exception {
        String merchantId = EnvironmentHelper.environment.getProperty(MERCHANT_ID, String.class);
        String partnerId = EnvironmentHelper.environment.getProperty(PARTNER_ID, String.class);

        String timestamp = TimeUtil.timeFormat(new Date(), TimeUtil.FormatPatternsEnum.TIMESTAMP);
        LinessBaseParam linessBaseParam = (LinessBaseParam) request.getEntity();

        linessBaseParam.setPartnerId(partnerId);
        linessBaseParam.setMerchantId(merchantId);
        linessBaseParam.setTimestamp(timestamp);
        linessBaseParam.setSignType(SIGN_TYPE);

        Map<String, Object> paramMap = JsonUtils.toMap(linessBaseParam);
        String md5 = getSign(paramMap);
        linessBaseParam.setSign(md5);

        request.setEntity(linessBaseParam);
    }

    private String getSign(Map<String, Object> param) throws NoSuchAlgorithmException {
        String partnerId = EnvironmentHelper.environment.getProperty(RAS_KEY, String.class);

        TreeMap<String, Object> sortMap = Maps.newTreeMap();
        sortMap.putAll(param);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if (Objects.nonNull(entry.getValue())) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sb.append("key=").append(partnerId);
        MessageDigest messageDigest = MessageDigest.getInstance("sha-256");
        messageDigest.update(sb.toString().getBytes(StandardCharsets.UTF_8));

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(messageDigest.digest());
    }

}
