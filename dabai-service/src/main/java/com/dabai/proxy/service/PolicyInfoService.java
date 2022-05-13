package com.dabai.proxy.service;

import com.dabai.proxy.dto.PolicyInfoDto;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:15
 */
public interface PolicyInfoService {

    void savePolicyInfo(Integer userId, BigDecimal commision, PolicyInfoDto policyInfoDto);
}
