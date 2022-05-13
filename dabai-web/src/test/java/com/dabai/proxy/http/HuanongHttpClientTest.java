package com.dabai.proxy.http;

import com.dabai.proxy.BaseTest;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 20:53
 */
@Slf4j
public class HuanongHttpClientTest extends BaseTest {

    @Autowired
    HuanongHttpClient huanongHttpClient;

    @Test
    public void testMemberInfo() {
        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone("15701299213");
        HuanongResult<MemberInfoResp> memerInfoRespHuanongResult = huanongHttpClient.memberInfo(memberInfoParam);
        log.info("memerInfoRespHuanongResult: {}", memerInfoRespHuanongResult);
    }
}
