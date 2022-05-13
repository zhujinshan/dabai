package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.WalletInfoMapper;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:59
 */
@Slf4j
@Service
public class WalletInfoServiceImpl implements WalletInfoService {

    @Resource
    private WalletInfoMapper walletInfoMapper;

    @Override
    public void addCommission(Integer userId, BigDecimal commission) {

    }

    @Override
    public void refundCommission(Integer userId, BigDecimal commission) {

    }
}
