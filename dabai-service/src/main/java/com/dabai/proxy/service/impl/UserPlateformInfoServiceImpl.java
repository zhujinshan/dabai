package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.UserPlateformInfoMapper;
import com.dabai.proxy.enums.HbxUserTag;
import com.dabai.proxy.enums.UserPlateformEnum;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.service.UserPlateformInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:31
 */
@Service
@Slf4j
public class UserPlateformInfoServiceImpl implements UserPlateformInfoService {

    @Resource
    private UserPlateformInfoMapper userPlateformInfoMapper;

    @Override
    public void save(Long userId, MemberInfoResp memberInfoResp) {
        Assert.notNull(userId, "userId缺失");
        Assert.notNull(memberInfoResp, "memberInfoResp缺失");
        UserPlateformInfo userPlateformInfo = getByUserId(userId);

        UserPlateformInfo currentInfo = new UserPlateformInfo();
        currentInfo.setPlateform(UserPlateformEnum.HBX.name());
        currentInfo.setIdentityTag(memberInfoResp.getIsAgent() == 1 ? HbxUserTag.AGENT.getCode().byteValue() : HbxUserTag.MEMBER.getCode().byteValue());
        currentInfo.setCode(memberInfoResp.getMemberNo());
        currentInfo.setOrganizationCode(memberInfoResp.getComCode());
        currentInfo.setUserId(userId);
        currentInfo.setUtime(new Date());
        if (Objects.nonNull(userPlateformInfo)) {
            currentInfo.setId(userPlateformInfo.getId());
            userPlateformInfoMapper.updateByPrimaryKeySelective(currentInfo);
        } else {
            currentInfo.setCtime(new Date());
            userPlateformInfoMapper.insertSelective(currentInfo);
        }
    }

    @Override
    public UserPlateformInfo getByHbxMemberNo(String memberNo) {
        Assert.notNull(memberNo, "memberNo缺失");
        Example example = new Example(UserPlateformInfo.class);
        example.createCriteria().andEqualTo("code", memberNo)
                .andEqualTo("plateform", UserPlateformEnum.HBX.name());
        return userPlateformInfoMapper.selectOneByExample(example);
    }

    @Override
    public UserPlateformInfo getByUserId(Long userId) {
        Assert.notNull(userId, "userId缺失");
        Example example = new Example(UserPlateformInfo.class);
        example.createCriteria().andEqualTo("userId", userId);
        return userPlateformInfoMapper.selectOneByExample(example);
    }
}
