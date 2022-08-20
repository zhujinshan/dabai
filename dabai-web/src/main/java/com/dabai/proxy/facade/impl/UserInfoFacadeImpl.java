package com.dabai.proxy.facade.impl;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dao.UserInfoMapper;
import com.dabai.proxy.dao.UserPlateformInfoMapper;
import com.dabai.proxy.dao.UserTagChangeMapper;
import com.dabai.proxy.dao.WalletInfoMapper;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.po.UserTagChange;
import com.dabai.proxy.po.WalletInfo;
import com.dabai.proxy.req.UserPlateformReq;
import com.dabai.proxy.resp.MemberInfoExport;
import com.dabai.proxy.resp.UserInfoResp;
import com.dabai.proxy.service.UserInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:16
 */
@Component
@Slf4j
public class UserInfoFacadeImpl implements UserInfoFacade {

    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private HuanongHttpClient huanongHttpClient;
    @Autowired
    private UserPlateformInfoService userPlateformInfoService;
    @Autowired
    private WalletInfoService walletInfoService;
    @Resource
    private UserTagChangeMapper userTagChangeMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveUserPhone(String openId, String phone, Long parentId) {
        Assert.notNull(openId, "openId信息缺失");
        Assert.notNull(phone, "phoneNo信息缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "未知用户信息");
        if (StringUtils.isNotEmpty(userInfo.getMobile())) {
            log.info("该用户手机号不为空，跳过处理，userInfo: {} ", userInfo);
            return;
        }
        if (Objects.nonNull(userInfo.getParentUserId()) && userInfo.getParentUserId() > 0) {
            parentId = userInfo.getParentUserId();
        }
        userInfoService.saveUserPhone(userInfo.getId(), phone, parentId);
        UserPlateformInfo parentPlateform = null;

        if (Objects.nonNull(parentId) && parentId > 0) {
            parentPlateform = userPlateformInfoService.getByUserId(parentId);
        }

        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(phone);
        if (Objects.nonNull(parentPlateform)) {
            memberInfoParam.setInviterNo(parentPlateform.getCode());
            memberInfoParam.setInviterComCode(parentPlateform.getOrganizationCode());
        }
        HuanongResult<MemberInfoResp> result = huanongHttpClient.memberInfo(memberInfoParam);
        if (result == null || !Objects.equals(result.getState(), "200")) {
            log.error("华农会员信息交互异常, result:{}", result);
            throw new HttpClientBusinessException("华农会员信息交互异常");
        }

        MemberInfoResp data = result.getData();
        if (Objects.isNull(data)) {
            return;
        }
        userPlateformInfoService.save(userInfo.getId(), data);
        // 钱包开户
        walletInfoService.addWallet(userInfo.getId());
    }

    @Override
    public UserInfoResp getUserInfo(String openId) {
        Assert.notNull(openId, "openId信息缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        if (userInfo == null) {
            return null;
        }
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtils.copyProperties(userInfo, userInfoResp);
        UserPlateformInfo plateformInfo = userPlateformInfoService.getByUserId(userInfo.getId());
        if (plateformInfo != null) {
            userInfoResp.setMemberNo(plateformInfo.getCode());
            userInfoResp.setIdentityTag(plateformInfo.getIdentityTag() != null ? plateformInfo.getIdentityTag().intValue() : 0);
        }
        WalletInfo walletInfo = walletInfoService.getWallet(userInfo.getId());
        if (walletInfo != null) {
            userInfoResp.setAvailableAmount(walletInfo.getAvailableAmount());
            userInfoResp.setCashedAmount(walletInfo.getCashedAmount());
            userInfoResp.setTotalAmount(walletInfo.getTotalAmount());
        }
        return userInfoResp;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateUserPlateformInfo(UserPlateformReq userPlateformReq) {
        UserPlateformInfo userPlateformInfo = userPlateformInfoService.getByHbxMemberNo(userPlateformReq.getCode());
        Assert.notNull(userPlateformInfo, "华保星编码不存在");

        Example example = new Example(UserTagChange.class);
        example.createCriteria().andEqualTo("userId", userPlateformInfo.getUserId());
        UserTagChange userTagChange = userTagChangeMapper.selectOneByExample(example);
        if (Objects.nonNull(userTagChange)) {
            if (!userTagChange.getCurrentIdentityTag().equals(Integer.valueOf(userPlateformReq.getIdentityTag()))) {
                userTagChange.setOriginalIdentityTag(userTagChange.getCurrentIdentityTag());
                userTagChange.setCurrentIdentityTag(Integer.valueOf(userPlateformReq.getIdentityTag()));
                userTagChange.setUtime(new Date());
                userTagChangeMapper.updateByPrimaryKey(userTagChange);
            }
        } else {
            if (!userPlateformInfo.getIdentityTag().equals(Byte.valueOf(userPlateformReq.getIdentityTag()))) {
                userTagChange = new UserTagChange();
                userTagChange.setUserId(userPlateformInfo.getUserId());
                userTagChange.setCtime(new Date());
                userTagChange.setUtime(new Date());
                userTagChange.setOriginalIdentityTag(userPlateformInfo.getIdentityTag().intValue());
                userTagChange.setCurrentIdentityTag(Integer.valueOf(userPlateformReq.getIdentityTag()));
                userTagChangeMapper.insertSelective(userTagChange);
            }
        }
        userPlateformInfo.setCode(userPlateformReq.getCode());
        userPlateformInfo.setIdentityTag(Byte.valueOf(userPlateformReq.getIdentityTag()));
        userPlateformInfo.setOrganizationCode(userPlateformReq.getOrganizationCode());
        userPlateformInfoService.updateUserPalteformInfo(userPlateformInfo);
    }

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserPlateformInfoMapper userPlateformInfoMapper;
    @Resource
    private WalletInfoMapper walletInfoMapper;
    @Resource
    private PolicyInfoMapper policyInfoMapper;

    @Override
    public List<MemberInfoExport> getAllUserInfo() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        Map<Long, UserInfo> userInfoMap =
                userInfos.stream().collect(Collectors.toMap(UserInfo::getId, e -> e));
        List<UserPlateformInfo> userPlateformInfos = userPlateformInfoMapper.selectAll();
        Map<Long, UserPlateformInfo> userPlateformInfoMap = userPlateformInfos.stream().collect(Collectors.toMap(UserPlateformInfo::getUserId, e -> e));
        List<WalletInfo> walletInfos = walletInfoMapper.selectAll();
        Map<Long, WalletInfo> walletInfoMap = walletInfos.stream().collect(Collectors.toMap(WalletInfo::getUserId, e -> e));

        List<PolicyInfo> policyInfos = policyInfoMapper.selectAll();
        Map<Long, BigDecimal> policyMap = Maps.newHashMap();
        for (PolicyInfo policyInfo : policyInfos) {
            Long userId = policyInfo.getUserId();
            if(policyInfo.getPolicyStatus().equals(PolicyStatus.COMPLETE.getCode())) {
                BigDecimal currentMoney = policyMap.getOrDefault(userId, BigDecimal.ZERO);
                currentMoney = currentMoney.add(policyInfo.getPremium());
                policyMap.put(userId, currentMoney);
            }
        }
        List<MemberInfoExport> allMemberInfo = Lists.newArrayList();
        for (UserInfo userInfo : userInfos) {
            Long id = userInfo.getId();
            UserPlateformInfo userPlateformInfo = userPlateformInfoMap.get(id);
            WalletInfo walletInfo = walletInfoMap.get(id);

            UserInfo parentUser = null;
            UserPlateformInfo parantPlateform = null;
            if(userInfo.getParentUserId() != null && userInfo.getParentUserId() > 0) {
                parentUser = userInfoMap.get(userInfo.getParentUserId());
                parantPlateform = userPlateformInfoMap.get(userInfo.getParentUserId());
            }

            if(userPlateformInfo == null) {
                continue;
            }

            MemberInfoExport memberInfoExport = new MemberInfoExport();
            memberInfoExport.setMemberNo(userPlateformInfo.getCode());
            memberInfoExport.setPhone(userInfo.getMobile());
            memberInfoExport.setIdCard(userInfo.getIdCard());
            memberInfoExport.setName(userInfo.getName());
            memberInfoExport.setOrganizationCode(userPlateformInfo.getOrganizationCode());
            memberInfoExport.setCtime(userInfo.getCtime());
            Byte identityTag = userPlateformInfo.getIdentityTag();
            memberInfoExport.setIdentityTag(identityTag != null && identityTag == 1 ? "是" : "否");
            memberInfoExport.setTotalFee(policyMap.getOrDefault(id, BigDecimal.ZERO));
            memberInfoExport.setTotalAmount(walletInfo.getTotalAmount());
            memberInfoExport.setTotalCashedAmount(walletInfo.getCashedAmount());
            if(parantPlateform != null && parentUser != null){
                memberInfoExport.setParentMemberNo(parantPlateform.getCode());
                memberInfoExport.setParentPhone(parentUser.getMobile());
            }
            allMemberInfo.add(memberInfoExport);
        }

        return allMemberInfo;
    }
}
