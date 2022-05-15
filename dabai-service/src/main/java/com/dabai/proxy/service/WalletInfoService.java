package com.dabai.proxy.service;

import com.dabai.proxy.po.CashSnapshot;
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


    /**
     * 提现中
     * @param userId
     * @param cashSnapshot
     */
    void cashing(Long userId, CashSnapshot cashSnapshot);

    /**
     * 提现成功
     * @param userId
     * @param cashSnapshot
     */
    void cashSuccess(Long userId, CashSnapshot cashSnapshot);

    /**
     * 提现失败
     * @param userId
     * @param cashSnapshot
     */
    void cashFailed(Long userId, CashSnapshot cashSnapshot);
}
