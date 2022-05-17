package com.dabai.proxy.facade.impl;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.facade.PolicyInfoFacade;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.Assert;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:40
 */
@Slf4j
@Service
public class PolicyInfoFacadeImpl implements PolicyInfoFacade {

    @Autowired
    private PolicyInfoService policyInfoService;
    @Autowired
    private WalletInfoService walletInfoService;
    @Autowired
    private UserPlateformInfoService userPlateformInfoService;

    @Value("${commission.radio}")
    private BigDecimal commissionRadio;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void policyComplete(PolicyInfoDto policyInfoDto) {
        Assert.notNull(policyInfoDto, "保单信息缺失");
        Assert.notNull(policyInfoDto.getChannelNo(), "保单会员编码缺失");
        Assert.notNull(policyInfoDto.getPolicyNo(), "保单号缺失");
        BigDecimal premium = policyInfoDto.getPremium();
        Assert.notNull(premium, "保单保费缺失");
        UserPlateformInfo hbxMemberInfo = userPlateformInfoService.getByHbxMemberNo(policyInfoDto.getChannelNo());
        if (hbxMemberInfo == null) {
            log.info("未找到平台用户，policyInfoDto：{}", policyInfoDto);
            return;
        }
        BigDecimal commission = premium.multiply(commissionRadio).setScale(2, BigDecimal.ROUND_FLOOR);
        log.info("保单佣金，premium：{}, commissionRadio:{}, commission:{}", premium, commissionRadio, commission);
        // 更新保单信息
        policyInfoService.savePolicyInfo(hbxMemberInfo.getUserId(), commission, PolicyStatus.COMPLETE, policyInfoDto);
        // 计算佣金
        walletInfoService.addCommission(hbxMemberInfo.getUserId(), commission, policyInfoDto.getPolicyNo());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void policyRefund(PolicyInfoDto policyInfoDto) {
        Assert.notNull(policyInfoDto, "保单信息缺失");
        Assert.notNull(policyInfoDto.getChannelNo(), "保单会员编码缺失");
        Assert.notNull(policyInfoDto.getPolicyNo(), "保单号缺失");
        BigDecimal premium = policyInfoDto.getPremium();
        Assert.notNull(premium, "保单保费缺失");
        UserPlateformInfo hbxMemberInfo = userPlateformInfoService.getByHbxMemberNo(policyInfoDto.getChannelNo());
        if (hbxMemberInfo == null) {
            log.info("未找到平台用户，policyInfoDto：{}", policyInfoDto);
            return;
        }
        BigDecimal commission = premium.multiply(commissionRadio).setScale(2, BigDecimal.ROUND_FLOOR);
        log.info("保单佣金，premium：{}, commissionRadio:{}, commission:{}", premium, commissionRadio, commission);
        // 更新保单信息
        policyInfoService.savePolicyInfo(hbxMemberInfo.getUserId(), commission, PolicyStatus.REFUND, policyInfoDto);
        // 计算佣金
        walletInfoService.refundCommission(hbxMemberInfo.getUserId(), commission, policyInfoDto.getPolicyNo());
    }

    @Override
    public void payTimeOut(PolicyInfoDto policyInfoDto) {

    }
}
