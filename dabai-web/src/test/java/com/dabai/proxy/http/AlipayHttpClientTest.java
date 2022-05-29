package com.dabai.proxy.http;

import com.dabai.proxy.BaseTest;
import com.dabai.proxy.httpclient.alipay.AlipayHttpClient;
import com.dabai.proxy.httpclient.alipay.resp.ValidateCardInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/29 17:32
 */
@Slf4j
public class AlipayHttpClientTest extends BaseTest {

    @Autowired
    private AlipayHttpClient alipayHttpClient;

    @Test
    public void testValidateCard() {
        ValidateCardInfoResp validateCardInfoResp = alipayHttpClient.validateAndCacheCardInfo("6217000130050086957", true);
        log.info("resp: {}", validateCardInfoResp);
    }
}
