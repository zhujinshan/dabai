package com.dabai.proxy.facade.impl;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.facade.PolicyInfoFacade;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:40
 */
@Slf4j
@Service
public class PolicyInfoFacadeImpl implements PolicyInfoFacade {

    private PolicyInfoService policyInfoService;

    private WalletInfoService walletInfoService;

    @Override
    public void policyComplete(PolicyInfoDto policyInfoDto) {
        // 更新保单信息

        // 计算佣金

        // 增加流水
    }

    @Override
    public void policyRefund(PolicyInfoDto policyInfoDto) {
        // 更新保单信息

        // 回退佣金

        // 增加流水
    }

    @Override
    public void payTimeOut(PolicyInfoDto policyInfoDto) {
        // 更新保单信息
    }
}
