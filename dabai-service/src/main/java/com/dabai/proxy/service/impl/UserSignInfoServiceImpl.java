package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.UserSignInfoMapper;
import com.dabai.proxy.enums.SignBusinessSourceEnum;
import com.dabai.proxy.enums.UserSignSourceEnum;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.po.UserSignInfo;
import com.dabai.proxy.service.UserSignInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 17:18
 */
@Service
@Slf4j
public class UserSignInfoServiceImpl implements UserSignInfoService {

    @Resource
    private UserSignInfoMapper userSignInfoMapper;

    @Override
    public UserSignInfo getByUserId(Long userId) {
        Assert.notNull(userId, "userId缺失");
        Example example = new Example(UserSignInfo.class);
        example.createCriteria().andEqualTo("userId", userId);
        return userSignInfoMapper.selectOneByExample(example);
    }

    @Override
    public UserSignInfo signing(Long userId, String signDealNo) {
        Assert.notNull(userId, "userId缺失");
        Assert.notNull(signDealNo, "signDealNo缺失");
        UserSignInfo userSignInfo = getByUserId(userId);
        UserSignInfo newUserSignInfo = new UserSignInfo();
        newUserSignInfo.setUserId(userId);
        newUserSignInfo.setUtime(new Date());
        newUserSignInfo.setSignStatus(UserSignStatusEnum.SIGNING.getCode());
        newUserSignInfo.setSignSource(UserSignSourceEnum.FC.name());
        newUserSignInfo.setBusinessSource(SignBusinessSourceEnum.RFT.name());
        newUserSignInfo.setSignDealNo(signDealNo);
        if (userSignInfo != null) {
            newUserSignInfo.setId(userSignInfo.getId());
            userSignInfoMapper.updateByPrimaryKeySelective(newUserSignInfo);
        } else {
            newUserSignInfo.setCtime(new Date());
            userSignInfoMapper.insertSelective(newUserSignInfo);
        }
        return newUserSignInfo;
    }

    @Override
    public UserSignInfo getByDealNo(String dealNo) {
        Assert.notNull(dealNo, "dealNo缺失");
        Example example = new Example(UserSignInfo.class);
        example.createCriteria().andEqualTo("signDealNo", dealNo);
        return userSignInfoMapper.selectOneByExample(example);
    }

    @Override
    public void updateSignInfo(UserSignInfo userSignInfo) {
        if (userSignInfo.getId() != null) {
            userSignInfo.setUtime(new Date());
            userSignInfoMapper.updateByPrimaryKeySelective(userSignInfo);
        }
    }
}
