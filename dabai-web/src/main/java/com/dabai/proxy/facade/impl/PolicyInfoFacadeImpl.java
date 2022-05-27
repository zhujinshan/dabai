package com.dabai.proxy.facade.impl;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.facade.PolicyInfoFacade;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.ProductInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

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
    @Autowired
    private ProductInfoService productInfoService;

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
        BigDecimal currentRadio = commissionRadio;
        ProductInfo productInfo = productInfoService.getByProductCode(policyInfoDto.getProductCode());
        if (Objects.nonNull(productInfo) && Objects.nonNull(productInfo.getCommissionRadio()) &&
                productInfo.getCommissionRadio().compareTo(BigDecimal.ZERO) > 0) {
            currentRadio = productInfo.getCommissionRadio().divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_FLOOR);
        }

        BigDecimal commission = premium.multiply(currentRadio).setScale(2, BigDecimal.ROUND_FLOOR);
        log.info("保单佣金，premium：{}, currentRadio:{}, commission:{}", premium, currentRadio, commission);
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
        UserPlateformInfo hbxMemberInfo = userPlateformInfoService.getByHbxMemberNo(policyInfoDto.getChannelNo());
        if (hbxMemberInfo == null) {
            log.info("未找到平台用户，policyInfoDto：{}", policyInfoDto);
            return;
        }
        PolicyInfo policyInfo = policyInfoService.queryByUserIdAndPolicyNo(hbxMemberInfo.getUserId(), policyInfoDto.getPolicyNo());
        log.info("保单信息退款，policyInfo:{}", policyInfo);

        // 更新保单信息
        policyInfoService.savePolicyInfo(hbxMemberInfo.getUserId(), policyInfo.getCommissionAmount(), PolicyStatus.REFUND, policyInfoDto);
        // 计算佣金
        walletInfoService.refundCommission(hbxMemberInfo.getUserId(), policyInfo.getCommissionAmount(), policyInfoDto.getPolicyNo());
    }

    @Override
    public void payTimeOut(PolicyInfoDto policyInfoDto) {

    }
}
