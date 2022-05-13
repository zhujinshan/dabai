package com.dabai.proxy.facade;

import com.dabai.proxy.dto.PolicyInfoDto;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:37
 */
public interface PolicyInfoFacade {

    /**
     * 保单完成
     * @param policyInfoDto
     */
    void policyComplete(PolicyInfoDto policyInfoDto);


    /**
     * 保单退款
     * @param policyInfoDto
     */
    void policyRefund(PolicyInfoDto policyInfoDto);


    /**
     * 超时未支付
     * @param policyInfoDto
     */
    void payTimeOut(PolicyInfoDto policyInfoDto);
}
