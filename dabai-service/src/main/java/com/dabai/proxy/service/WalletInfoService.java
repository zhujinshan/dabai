package com.dabai.proxy.service;

import com.dabai.proxy.po.WalletInfo;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:44
 */
public interface WalletInfoService {

    /**
     * 钱包开户
     * @param userId
     * @return
     */
    Long addWallet(Long userId);

    /**
     * 查询钱包
     * @param userId
     * @return
     */
    WalletInfo getWallet(Long userId);

    /**
     * 增加佣金
     * @param commission
     */
    void addCommission(Long userId, BigDecimal commission, String policyNo);


    /**
     * 减少佣金
     * @param commission
     */
    void refundCommission(Long userId, BigDecimal commission, String policyNo);
}
