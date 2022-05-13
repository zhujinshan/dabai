package com.dabai.proxy.httpclient.huanong;

import com.dabai.proxy.httpclient.api.BeforeRequest;
import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.HttpClient;
import com.dabai.proxy.httpclient.api.HttpEntity;
import com.dabai.proxy.httpclient.api.HttpMapping;
import com.dabai.proxy.httpclient.api.RequestMethod;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberForwardStarResp;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 18:42
 */
@HttpClient
@HttpMapping("${http.huanong.host}")
@BeforeRequest(HuanongHttpClientProcessor.class)
public interface HuanongHttpClient {

    @HttpMapping(path = "/middleStage/api/member/userInfo", method = RequestMethod.POST,
            dataType = DataType.JSON)
    HuanongResult<MemberInfoResp> memberInfo(@HttpEntity MemberInfoParam memberInfoParam);


    @HttpMapping(path = "/middleStage/api/member/forwardStarMini", method = RequestMethod.POST,
            dataType = DataType.JSON)
    HuanongResult<MemberForwardStarResp> forwardStarMini(@HttpEntity MemberInfoParam memberInfoParam);

}
