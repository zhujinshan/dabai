package com.dabai.proxy.http;

import com.dabai.proxy.BaseTest;
import com.dabai.proxy.controller.PolicyInfoController;
import com.dabai.proxy.httpclient.tencentcloud.MailSendClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jinshan.zhu
 * @date: 2022/9/7 23:07
 */
public class MailSendClientTest extends BaseTest {

    @Autowired
    private PolicyInfoController policyInfoController;

    @Autowired
    private MailSendClient mailSendClient;

    @Test
    public void testSend() {
        //mailSendClient.sendMail("1066391791@qq.com", "X0027263422000227360", "华保健康百万医疗保险2020H版", "https://hn-imgs.oss-cn-shanghai-finance-1-pub.aliyuncs.com/7b4aff8cef322c1f33fc890877cfb07a.pdf");
        //policyInfoController.sendMail("X0027263422000227360", "1528094996@qq.com");
    }
}
