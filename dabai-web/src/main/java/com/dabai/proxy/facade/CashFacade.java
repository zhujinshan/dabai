package com.dabai.proxy.facade;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.req.UserCashSubmitReq;
import com.dabai.proxy.req.UserSignReq;
import com.dabai.proxy.resp.UserCashSignInfoResp;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:42
 */
public interface CashFacade {

    /**
     * 提现基本信息
     * @param openId openId
     * @return resp
     */
    UserCashSignInfoResp cashInfo(String openId);

    /**
     * 签约接口
     * @param openId
     * @param userSignReq
     */
    UserCashSignInfoResp sign(String openId, UserSignReq userSignReq);

    /**
     * 提现
     * @param openId
     * @param cashSubmitReq
     */
    Result<String> cashSubmit(String openId, UserCashSubmitReq cashSubmitReq);
}
