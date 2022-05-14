package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dto.PolicyInfoDto;
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
    public void savePolicyInfo(Long userId, BigDecimal commision, PolicyInfoDto policyInfoDto) {
        Assert.notNull(userId, "userId缺失");
        Assert.notNull(policyInfoDto, "policyInfoDto信息缺失");
        Assert.notNull(policyInfoDto.getPolicyNo(), "保单号信息缺失");

        String policyNo = policyInfoDto.getPolicyNo();
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("policyNo", policyNo);
        PolicyInfo policyInfo = policyInfoMapper.selectOneByExample(example);

        PolicyInfo newPolicyInfo = new PolicyInfo();
        BeanUtils.copyProperties(policyInfoDto, newPolicyInfo);
        newPolicyInfo.setCommissionAmount(commision);
        if (policyInfo != null) {
            newPolicyInfo.setUtime(new Date());
            newPolicyInfo.setId(policyInfo.getId());
            policyInfoMapper.updateByPrimaryKeySelective(newPolicyInfo);
        } else {
            newPolicyInfo.setCtime(new Date());
            policyInfoMapper.insertSelective(newPolicyInfo);
        }
    }
}
