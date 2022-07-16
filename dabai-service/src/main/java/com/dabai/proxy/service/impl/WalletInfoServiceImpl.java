package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.CashSnapshotMapper;
import com.dabai.proxy.dao.WalletFlowMapper;
import com.dabai.proxy.dao.WalletInfoMapper;
import com.dabai.proxy.enums.CashStatusEnum;
import com.dabai.proxy.enums.MannualChargeTypeEnum;
import com.dabai.proxy.enums.WalletFlowTypeEnum;
import com.dabai.proxy.lock.JdkLockFunction;
import com.dabai.proxy.lock.LockObject;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.WalletFlow;
import com.dabai.proxy.po.WalletInfo;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:59
 */
@Slf4j
@Service
public class WalletInfoServiceImpl implements WalletInfoService {

    private static final String LOCK_KEY = "wallet";
    @Resource
    private WalletInfoMapper walletInfoMapper;
    @Resource
    private JdkLockFunction jdkLockFunction;
    @Resource
    private WalletFlowMapper walletFlowMapper;
    @Resource
    private CashSnapshotMapper cashSnapshotMapper;

    @Override
    public Long addWallet(Long userId) {
        Assert.notNull(userId, "userId缺失");
        WalletInfo wallet = getWallet(userId);
        if (wallet != null) {
            return wallet.getId();
        } else {
            WalletInfo walletInfo1 = new WalletInfo();
            walletInfo1.setAvailableAmount(BigDecimal.ZERO);
            walletInfo1.setCashedAmount(BigDecimal.ZERO);
            walletInfo1.setCtime(new Date());
            walletInfo1.setUserId(userId);
            walletInfo1.setTotalAmount(BigDecimal.ZERO);
            walletInfo1.setCashedAmount(BigDecimal.ZERO);
            walletInfo1.setCashingAmount(BigDecimal.ZERO);
            walletInfo1.setUtime(new Date());
            walletInfoMapper.insertSelective(walletInfo1);
            return walletInfo1.getId();
        }
    }

    @Override
    public WalletInfo getWallet(Long userId) {
        Assert.notNull(userId, "userId缺失");
        Example example = new Example(WalletInfo.class);
        example.createCriteria().andEqualTo("userId", userId);
        return walletInfoMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addCommission(Long userId, BigDecimal commission, String policyNo) {
        Assert.notNull(userId, "userId缺失");
        Assert.isTrue(StringUtils.isNotEmpty(policyNo), "保单编号缺失");
        if (commission == null || commission.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
            WalletInfo walletInfo = getWallet(userId);
            Example example1 = new Example(WalletFlow.class);
            example1.createCriteria().andEqualTo("flowType", WalletFlowTypeEnum.ADD.getCode())
                    .andEqualTo("policyNo", policyNo)
                    .andEqualTo("userId", userId);

            WalletFlow walletFlow = walletFlowMapper.selectOneByExample(example1);
            if (Objects.nonNull(walletFlow)) {
                log.error("该笔保单已经进入佣金，无需重复计入");
                return;
            }
            Long walletId;
            if (Objects.isNull(walletInfo)) {
                WalletInfo walletInfo1 = new WalletInfo();
                walletInfo1.setAvailableAmount(commission);
                walletInfo1.setCashedAmount(BigDecimal.ZERO);
                walletInfo1.setCtime(new Date());
                walletInfo1.setUserId(userId);
                walletInfo1.setTotalAmount(commission);
                walletInfo1.setCashedAmount(BigDecimal.ZERO);
                walletInfo1.setCashingAmount(BigDecimal.ZERO);
                walletInfo1.setUtime(new Date());
                walletInfoMapper.insertSelective(walletInfo1);
                walletId = walletInfo1.getId();
            } else {
                walletId = walletInfo.getId();
                WalletInfo walletInfo2 = new WalletInfo();
                walletInfo2.setId(walletInfo.getId());
                walletInfo2.setTotalAmount(walletInfo.getTotalAmount().add(commission));
                walletInfo2.setAvailableAmount(walletInfo.getAvailableAmount().add(commission));
                walletInfo2.setUtime(new Date());
                walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);
            }

            WalletFlow walletFlowNew = new WalletFlow();
            walletFlowNew.setWalletId(walletId);
            walletFlowNew.setAmount(commission);
            walletFlowNew.setFlowType(WalletFlowTypeEnum.ADD.getCode());
            walletFlowNew.setCtime(new Date());
            walletFlowNew.setPolicyNo(policyNo);
            walletFlowNew.setUserId(userId);
            walletFlowNew.setUtime(new Date());
            walletFlowMapper.insertSelective(walletFlowNew);
        });
    }

    @Override
    public void refundCommission(Long userId, BigDecimal commission, String policyNo) {
        Assert.notNull(userId, "userId缺失");
        Assert.isTrue(StringUtils.isNotEmpty(policyNo), "保单编号缺失");
        if (commission == null || commission.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
            WalletInfo walletInfo = getWallet(userId);
            if (walletInfo == null) {
                log.info("钱包不能存在跳过处理，userId:{}", userId);
                return;
            }
            Example example1 = new Example(WalletFlow.class);
            example1.createCriteria().andEqualTo("policyNo", policyNo)
                    .andEqualTo("userId", userId);

            List<WalletFlow> walletFlowList = walletFlowMapper.selectByExample(example1);
            if (CollectionUtils.isEmpty(walletFlowList)) {
                log.error("该笔保单暂未计入佣金，无需处理");
                return;
            }
            if (walletFlowList.size() > 1) {
                long count = walletFlowList.stream().filter(e -> e.getFlowType().equals(WalletFlowTypeEnum.REFUND.getCode()))
                        .count();
                if (count > 0) {
                    log.error("该笔保单已经处理过退款，无需重复处理");
                    return;
                }
            }

            WalletInfo walletInfo2 = new WalletInfo();
            walletInfo2.setId(walletInfo.getId());
            walletInfo2.setTotalAmount(walletInfo.getTotalAmount().subtract(commission));
            walletInfo2.setAvailableAmount(walletInfo.getAvailableAmount().subtract(commission));
            walletInfo2.setUtime(new Date());
            walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);

            WalletFlow walletFlowNew = new WalletFlow();
            walletFlowNew.setWalletId(walletInfo.getId());
            walletFlowNew.setAmount(commission);
            walletFlowNew.setFlowType(WalletFlowTypeEnum.REFUND.getCode());
            walletFlowNew.setCtime(new Date());
            walletFlowNew.setPolicyNo(policyNo);
            walletFlowNew.setUserId(userId);
            walletFlowNew.setUtime(new Date());
            walletFlowMapper.insertSelective(walletFlowNew);
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void cashing(Long userId, CashSnapshot cashSnapshot) {
        Assert.notNull(userId, "userId缺失");
        BigDecimal cashedAmount = cashSnapshot.getCashedAmount();
        if (cashedAmount != null) {
            jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
                if (cashSnapshot.getId() != null) {
                    CashSnapshot cashSnapshotUpdate = new CashSnapshot();
                    cashSnapshotUpdate.setId(cashSnapshot.getId());
                    cashSnapshotUpdate.setStatus(CashStatusEnum.CASHING.getCode());
                    cashSnapshotUpdate.setDealNo(cashSnapshot.getDealNo());
                    cashSnapshotUpdate.setRemark(cashSnapshot.getRemark());
                    cashSnapshotUpdate.setUtime(new Date());
                    cashSnapshotMapper.updateByPrimaryKeySelective(cashSnapshotUpdate);
                }

                WalletInfo walletInfo = getWallet(userId);
                if (walletInfo == null) {
                    log.info("钱包不能存在跳过处理，userId:{}", userId);
                    return;
                }
                WalletInfo walletInfo2 = new WalletInfo();
                walletInfo2.setId(walletInfo.getId());
                BigDecimal currentAvailable = walletInfo.getAvailableAmount() == null ? BigDecimal.ZERO : walletInfo.getAvailableAmount();
                walletInfo2.setAvailableAmount(currentAvailable.subtract(cashedAmount));
                BigDecimal currentCashing = walletInfo.getCashingAmount() == null ? BigDecimal.ZERO : walletInfo.getCashingAmount();
                walletInfo2.setCashingAmount(currentCashing.add(cashedAmount));
                walletInfo2.setUtime(new Date());
                walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void cashSuccess(Long userId, CashSnapshot cashSnapshot) {
        Assert.notNull(userId, "userId缺失");

        BigDecimal cashedAmount = cashSnapshot.getCashedAmount();
        if (cashedAmount != null) {
            jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
                if (cashSnapshot.getId() != null) {
                    CashSnapshot cashSnapshotUpdate = new CashSnapshot();
                    cashSnapshotUpdate.setId(cashSnapshot.getId());
                    cashSnapshotUpdate.setStatus(CashStatusEnum.SUCCESS.getCode());
                    cashSnapshotUpdate.setUtime(new Date());
                    cashSnapshotMapper.updateByPrimaryKeySelective(cashSnapshotUpdate);
                }
                WalletInfo walletInfo = getWallet(userId);
                if (walletInfo == null) {
                    log.info("钱包不能存在跳过处理，userId:{}", userId);
                    return;
                }
                BigDecimal cashingAmount = walletInfo.getCashingAmount();
                WalletInfo walletInfo2 = new WalletInfo();
                walletInfo2.setId(walletInfo.getId());
                // 判断当前冻结金额是否大于该笔提现成功金额 避免异常请求变为负值
                if (cashingAmount.compareTo(cashedAmount) > -1) {
                    walletInfo2.setCashingAmount(cashingAmount.subtract(cashedAmount));
                }
                walletInfo2.setUtime(new Date());
                walletInfo2.setLastCashTime(new Date());
                walletInfo2.setCashedAmount(walletInfo.getCashedAmount().add(cashedAmount));
                walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);

                // 流水表
                WalletFlow walletFlowNew = new WalletFlow();
                walletFlowNew.setWalletId(walletInfo.getId());
                walletFlowNew.setAmount(cashedAmount);
                walletFlowNew.setFlowType(WalletFlowTypeEnum.WITHDRAWAL.getCode());
                walletFlowNew.setCtime(new Date());
                walletFlowNew.setCashRequestNo(cashSnapshot.getRequestNo());
                walletFlowNew.setUserId(userId);
                walletFlowNew.setUtime(new Date());
                walletFlowMapper.insertSelective(walletFlowNew);
            });
        }
    }

    @Override
    public void cashFailed(Long userId, CashSnapshot cashSnapshot) {
        Assert.notNull(userId, "userId缺失");

        BigDecimal cashedAmount = cashSnapshot.getCashedAmount();
        if (cashedAmount != null) {
            jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
                if (cashSnapshot.getId() != null) {
                    CashSnapshot cashSnapshotUpdate = new CashSnapshot();
                    cashSnapshotUpdate.setId(cashSnapshot.getId());
                    cashSnapshotUpdate.setStatus(CashStatusEnum.FAILED.getCode());
                    cashSnapshotUpdate.setUtime(new Date());
                    cashSnapshotUpdate.setRemark(cashSnapshot.getRemark());
                    cashSnapshotUpdate.setThirdResponse(cashSnapshot.getThirdResponse());
                    cashSnapshotMapper.updateByPrimaryKeySelective(cashSnapshotUpdate);
                }
                WalletInfo walletInfo = getWallet(userId);
                if (walletInfo == null) {
                    log.info("钱包不能存在跳过处理，userId:{}", userId);
                    return;
                }
                WalletInfo walletInfo2 = new WalletInfo();
                walletInfo2.setId(walletInfo.getId());
                walletInfo2.setAvailableAmount(walletInfo.getAvailableAmount().add(cashedAmount));
                // 判断当前冻结金额是否大于该笔提现成功金额
                BigDecimal cashingAmount = walletInfo.getCashingAmount();
                if (cashingAmount.compareTo(cashedAmount) > -1) {
                    walletInfo2.setCashingAmount(cashingAmount.subtract(cashedAmount));
                }
                walletInfo2.setUtime(new Date());
                walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void mannualCharge(Long userId, BigDecimal amount, MannualChargeTypeEnum chargeType) {
        Assert.notNull(userId, "userId缺失");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        jdkLockFunction.execute(LockObject.of(LOCK_KEY, userId), () -> {
            WalletInfo walletInfo = getWallet(userId);
            if (Objects.nonNull(walletInfo)) {
                WalletInfo walletInfo2 = new WalletInfo();
                walletInfo2.setId(walletInfo.getId());
                walletInfo2.setTotalAmount(walletInfo.getTotalAmount().add(amount));
                walletInfo2.setAvailableAmount(walletInfo.getAvailableAmount().add(amount));
                walletInfo2.setUtime(new Date());
                walletInfoMapper.updateByPrimaryKeySelective(walletInfo2);


                WalletFlow walletFlowNew = new WalletFlow();
                walletFlowNew.setWalletId(walletInfo.getId());
                walletFlowNew.setAmount(amount);
                walletFlowNew.setFlowType(WalletFlowTypeEnum.MANUAL.getCode());
                walletFlowNew.setCtime(new Date());
                walletFlowNew.setUserId(userId);
                walletFlowNew.setUtime(new Date());
                if (chargeType != null) {
                    walletFlowNew.setManualChargeType(chargeType.name());
                }
                walletFlowMapper.insertSelective(walletFlowNew);
            }
        });
    }
}
