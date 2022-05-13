package com.dabai.proxy.service;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:44
 */
public interface WalletInfoService {

    /**
     * 增加佣金
     * @param commission
     */
    void addCommission(Integer userId, BigDecimal commission);


    /**
     * 减少佣金
     * @param commission
     */
    void refundCommission(Integer userId, BigDecimal commission);
}
