package com.dabai.proxy.facade;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dao.ProductInfoMapper;
import com.dabai.proxy.dao.UserInfoCustomMapper;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.po.UserInfoQueryResult;
import com.dabai.proxy.query.MemberInfoQuery;
import com.dabai.proxy.req.MemberInfoQueryReq;
import com.dabai.proxy.req.MemberPerformQueryReq;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.resp.MemberInfoQueryResp;
import com.dabai.proxy.resp.MemberPerformQueryResp;
import com.dabai.proxy.resp.MemberPolicyDTO;
import com.dabai.proxy.resp.UserInfoQueryDTO;
import com.dabai.proxy.resp.UserPerformQueryDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 18:09
 */
@Component
public class MemberInfoFacade {

    private final String HEALTH_CAT = "健康险";

    private final String FINANCE_CAT = "财产险";

    private final String ACCIDENT_CAT = "意外险";

    @Resource
    private UserInfoCustomMapper userInfoCustomMapper;

    @Resource
    private PolicyInfoMapper policyInfoMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;

    public MemberInfoQueryResp query(MemberInfoQueryReq memberInfoQueryReq) {
        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        BeanUtils.copyProperties(memberInfoQueryReq, memberInfoQuery);

        Paging paging = memberInfoQueryReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }

        Page<UserInfoQueryResult> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> userInfoCustomMapper.queryUserInfo(memberInfoQuery));

        MemberInfoQueryResp resp = new MemberInfoQueryResp();
        resp.setTotal(pageResult.getTotal());
        List<UserInfoQueryResult> result = pageResult.getResult();
        List<UserInfoQueryDTO> userInfoQuerys = result.stream().map(e -> {
            UserInfoQueryDTO userInfoQueryDTO = new UserInfoQueryDTO();
            BeanUtils.copyProperties(e, userInfoQueryDTO);
            UserSignStatusEnum signStatusEnum = UserSignStatusEnum.getByCode(e.getSignStatus());
            if (Objects.equals(signStatusEnum, UserSignStatusEnum.SUCCESS)) {
                userInfoQueryDTO.setStatus(1);
                userInfoQueryDTO.setRealNameTime(e.getSignUtime());
            } else {
                userInfoQueryDTO.setStatus(0);
            }
            userInfoQueryDTO.setChangeAgent(false);
            if (Objects.nonNull(e.getOriginalIdentityTag()) && e.getOriginalIdentityTag() == 0
                    && Objects.nonNull(e.getCurrentIdentityTag()) && e.getCurrentIdentityTag() == 1) {
                userInfoQueryDTO.setChangeAgent(true);
                userInfoQueryDTO.setChangeAgentTime(e.getIdentityChangeTime());
            }
            return userInfoQueryDTO;
        }).collect(Collectors.toList());
        resp.setUserList(userInfoQuerys);
        return resp;
    }

    public MemberPerformQueryResp performQuery(MemberPerformQueryReq memberPerformQueryReq) {
        MemberPerformQueryResp resp = new MemberPerformQueryResp();
        if (Objects.isNull(memberPerformQueryReq) || Objects.isNull(memberPerformQueryReq.getStartTime()) ||
                Objects.isNull(memberPerformQueryReq.getEndTime())) {
            resp.setTotal(0L);
            return resp;
        }

        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        BeanUtils.copyProperties(memberPerformQueryReq, memberInfoQuery);
        Paging paging = memberPerformQueryReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }
        Page<UserInfoQueryResult> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> userInfoCustomMapper.queryUserInfo(memberInfoQuery));

        resp.setTotal(pageResult.getTotal());

        List<UserInfoQueryResult> result = pageResult.getResult();
        if (CollectionUtils.isEmpty(result)) {
            return resp;
        }
        List<Long> userIds = result.stream().map(UserInfoQueryResult::getId).collect(Collectors.toList());
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andIn("userId", userIds)
                .andEqualTo("policyStatus", PolicyStatus.COMPLETE.getCode());

        List<PolicyInfo> policyInfos = policyInfoMapper.selectByExample(example);
        Map<Long, List<PolicyInfo>> userPolicyMap = policyInfos.stream().collect(Collectors.groupingBy(PolicyInfo::getUserId));

        List<ProductInfo> productInfos = productInfoMapper.selectAll();
        Map<String, String> productTypeMap = Maps.newHashMap();
        for (ProductInfo productInfo : productInfos) {
            productTypeMap.put(productInfo.getCode(), productInfo.getCategory().trim());
        }

        List<UserPerformQueryDTO> collect = result.stream().map(e -> {
            UserPerformQueryDTO userPerformQueryDTO = new UserPerformQueryDTO();
            BeanUtils.copyProperties(e, userPerformQueryDTO);
            userPerformQueryDTO.setChangeAgent(false);
            if (Objects.nonNull(e.getOriginalIdentityTag()) && e.getOriginalIdentityTag() == 0
                    && Objects.nonNull(e.getCurrentIdentityTag()) && e.getCurrentIdentityTag() == 1) {
                userPerformQueryDTO.setChangeAgent(true);
            }
            List<PolicyInfo> policyInfos1 = userPolicyMap.get(e.getId());
            BigDecimal healthFee = BigDecimal.ZERO;
            BigDecimal healthProFee = BigDecimal.ZERO;
            BigDecimal financeFee = BigDecimal.ZERO;
            BigDecimal financeProFee = BigDecimal.ZERO;
            BigDecimal accidentFee = BigDecimal.ZERO;
            BigDecimal accidentProFee = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(policyInfos1)) {
                for (PolicyInfo policyInfo : policyInfos1) {
                    String productCode = policyInfo.getProductCode();
                    String category = productTypeMap.get(productCode);
                    if (StringUtils.isEmpty(category)) {
                        continue;
                    }
                    switch (category) {
                        case HEALTH_CAT:
                            healthFee = healthFee.add(policyInfo.getPremium());
                            healthProFee = healthProFee.add(policyInfo.getCommissionAmount());
                            break;
                        case FINANCE_CAT:
                            financeFee = financeFee.add(policyInfo.getPremium());
                            financeProFee = financeProFee.add(policyInfo.getCommissionAmount());
                            break;
                        case ACCIDENT_CAT:
                            accidentFee = accidentFee.add(policyInfo.getPremium());
                            accidentProFee = accidentProFee.add(policyInfo.getCommissionAmount());
                            break;
                        default:
                            break;
                    }
                }
            }
            userPerformQueryDTO.setHealthInsuranceFee(healthFee);
            userPerformQueryDTO.setHealthInsurancePromotionFee(healthProFee);
            userPerformQueryDTO.setAccidentInsuranceFee(accidentFee);
            userPerformQueryDTO.setAccidentInsurancePromotionFee(accidentProFee);
            userPerformQueryDTO.setPropertyInsuranceFee(financeFee);
            userPerformQueryDTO.setPropertyInsurancePromotionFee(financeProFee);
            userPerformQueryDTO.setTotalFee(healthFee.add(accidentFee).add(financeFee));
            userPerformQueryDTO.setTotalPromotionFee(healthProFee.add(accidentProFee).add(accidentProFee));
            return userPerformQueryDTO;
        }).collect(Collectors.toList());
        resp.setUserPerformList(collect);

        return resp;
    }


    public List<MemberPolicyDTO> policyList(Long userId) {
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("policyStatus", PolicyStatus.COMPLETE.getCode());
        List<PolicyInfo> policyInfos = policyInfoMapper.selectByExample(example);

        return policyInfos.stream().map(e -> {
            MemberPolicyDTO memberPolicyDTO = new MemberPolicyDTO();
            BeanUtils.copyProperties(e, memberPolicyDTO);
            return memberPolicyDTO;
        }).collect(Collectors.toList());
    }
}
