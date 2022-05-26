package com.dabai.proxy.service;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.po.PolicyInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:15
 */
public interface PolicyInfoService {

    /**
     * 更新保单信息
     *  @param userId
     * @param commision
     * @param complete
     * @param policyInfoDto
     */
    void savePolicyInfo(Long userId, BigDecimal commision, PolicyStatus complete, PolicyInfoDto policyInfoDto);

    /**
     * 查询保单列表
     *
     * @param userId 用户id
     * @param status 状态
     * @return resut
     */
    List<PolicyInfo> pageQuery(Long userId, Integer status, Long policyId);
}
