package com.dabai.proxy.httpclient.alipay;

import com.dabai.proxy.httpclient.alipay.resp.ValidateCardInfoResp;
import com.dabai.proxy.httpclient.api.HttpClient;
import com.dabai.proxy.httpclient.api.HttpMapping;
import com.dabai.proxy.httpclient.api.RequestMethod;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/29 17:20
 */
@HttpClient
public interface AlipayHttpClient {

    @HttpMapping(path = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json", method = RequestMethod.GET)
    ValidateCardInfoResp validateAndCacheCardInfo(String cardNo, Boolean cardBinCheck);

}
