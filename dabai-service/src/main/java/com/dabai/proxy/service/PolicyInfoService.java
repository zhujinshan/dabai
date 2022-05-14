package com.dabai.proxy.service;

import com.dabai.proxy.dto.PolicyInfoDto;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:15
 */
public interface PolicyInfoService {

    /**
     * 更新保单信息
     * @param userId
     * @param commision
     * @param policyInfoDto
     */
    void savePolicyInfo(Long userId, BigDecimal commision, PolicyInfoDto policyInfoDto);
}
