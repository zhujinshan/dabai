package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.service.PolicyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
 * @date: 2022/5/14 01:50
 */
@Service
@Slf4j
public class PolicyInfoServiceImpl implements PolicyInfoService {

    @Resource
    PolicyInfoMapper policyInfoMapper;

    @Override
    public void savePolicyInfo(Long userId, BigDecimal commision, PolicyStatus status, PolicyInfoDto policyInfoDto) {
        Assert.notNull(userId, "userId缺失");
        Assert.notNull(policyInfoDto, "policyInfoDto信息缺失");
        Assert.notNull(policyInfoDto.getPolicyNo(), "保单号信息缺失");
        Assert.notNull(status, "保单状态缺失");

        String policyNo = policyInfoDto.getPolicyNo();
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("policyNo", policyNo);
        PolicyInfo policyInfo = policyInfoMapper.selectOneByExample(example);

        PolicyInfo newPolicyInfo = new PolicyInfo();
        BeanUtils.copyProperties(policyInfoDto, newPolicyInfo);
        newPolicyInfo.setUserId(userId);
        newPolicyInfo.setCommissionAmount(commision);
        newPolicyInfo.setPolicyStatus(status.getCode());
        if (policyInfo != null) {
            newPolicyInfo.setUtime(new Date());
            newPolicyInfo.setId(policyInfo.getId());
            policyInfoMapper.updateByPrimaryKeySelective(newPolicyInfo);
        } else {
            newPolicyInfo.setCtime(new Date());
            newPolicyInfo.setUtime(new Date());
            policyInfoMapper.insertSelective(newPolicyInfo);
        }
    }


    @Override
    public List<PolicyInfo> pageQuery(Long userId, Integer status, Long policyId) {
        Assert.notNull(userId, "userId is required");
        Example example = new Example(PolicyInfo.class);
        example.setOrderByClause("id desc");
        Example.Criteria criteria = example.createCriteria().andEqualTo("userId", userId);
        if (Objects.nonNull(status)) {
            criteria.andEqualTo("policyStatus", status);
        }
        if (Objects.nonNull(policyId) && policyId > 0) {
            criteria.andEqualTo("id", policyId);
        }
        return policyInfoMapper.selectByExample(example);
    }
}
