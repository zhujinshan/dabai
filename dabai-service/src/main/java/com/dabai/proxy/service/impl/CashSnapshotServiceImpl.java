package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.CashSnapshotMapper;
import com.dabai.proxy.enums.CashStatusEnum;
import com.dabai.proxy.enums.SignBusinessSourceEnum;
import com.dabai.proxy.enums.UserSignSourceEnum;
import com.dabai.proxy.httpclient.liness.param.TransferToBankCardParam;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.service.CashSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 23:01
 */
@Service
@Slf4j
public class CashSnapshotServiceImpl implements CashSnapshotService {

    @Resource
    private CashSnapshotMapper cashSnapshotMapper;

    @Override
    public CashSnapshot init(Long userId, String mobile, TransferToBankCardParam transferToBankCardParam) {
        CashSnapshot cashSnapshot = new CashSnapshot();
        cashSnapshot.setUserId(userId);
        cashSnapshot.setBankCard(transferToBankCardParam.getBankCardNo());
        cashSnapshot.setCashedAmount(BigDecimal.valueOf(Double.parseDouble(transferToBankCardParam.getAmount())));
        cashSnapshot.setRequestNo(transferToBankCardParam.getRequestNo());
        cashSnapshot.setName(transferToBankCardParam.getName());
        cashSnapshot.setIdCard(transferToBankCardParam.getCertificateNo());
        cashSnapshot.setMobile(mobile);
        cashSnapshot.setSignSource(UserSignSourceEnum.ZX.name());
        cashSnapshot.setBusinessSource(SignBusinessSourceEnum.RFT.name());
        cashSnapshot.setStatus(CashStatusEnum.INIT.getCode());
        cashSnapshot.setCtime(new Date());
        cashSnapshot.setUtime(new Date());
        cashSnapshotMapper.insertSelective(cashSnapshot);
        return cashSnapshot;
    }

    @Override
    public void cashFailed(Long id, String msg, String thirdResp) {
        Assert.isTrue(id != null && id > 0, "提现快照id缺失");
        CashSnapshot cashSnapshot = new CashSnapshot();
        cashSnapshot.setId(id);
        cashSnapshot.setRemark(msg);
        cashSnapshot.setStatus(CashStatusEnum.FAILED.getCode());
        cashSnapshot.setThirdResponse(thirdResp);
        cashSnapshotMapper.updateByPrimaryKeySelective(cashSnapshot);
    }

    @Override
    public CashSnapshot getByRequestNo(String requestNo) {
        Assert.isTrue(StringUtils.isNotEmpty(requestNo), "requestNo缺失");
        Example example = new Example(CashSnapshot.class);
        example.createCriteria().andEqualTo("requestNo", requestNo);
        return cashSnapshotMapper.selectOneByExample(example);
    }

    @Override
    public List<CashSnapshot> pageQuery(Long userId) {
        Assert.notNull(userId, "userId is required");
        Example example = new Example(CashSnapshot.class);
        example.setOrderByClause("id desc");
        example.createCriteria().andEqualTo("userId", userId);
        return cashSnapshotMapper.selectByExample(example);
    }
}
