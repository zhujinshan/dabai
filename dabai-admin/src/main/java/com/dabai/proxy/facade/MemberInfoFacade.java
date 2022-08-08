package com.dabai.proxy.facade;

import com.dabai.proxy.dao.*;
import com.dabai.proxy.enums.PolicyStatus;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.enums.WalletFlowTypeEnum;
import com.dabai.proxy.po.*;
import com.dabai.proxy.query.MemberInfoQuery;
import com.dabai.proxy.req.*;
import com.dabai.proxy.resp.*;
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
import java.util.ArrayList;
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
    private WalletFlowMapper walletFlowMapper;

    @Resource
    private WalletInfoMapper walletInfoMapper;

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

    public MemberWalletFlowQueryResp walletFlowQuery(MemberWalletFlowQueryReq memberWalletFlowQueryReq) {
        MemberWalletFlowQueryResp resp = new MemberWalletFlowQueryResp();
        List<UserWalletFlowQueryDTO> userWalletList = new ArrayList<>();
        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        BeanUtils.copyProperties(memberWalletFlowQueryReq, memberInfoQuery);
        Paging paging = memberWalletFlowQueryReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }
        Page<WalletFlow> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> {
                    Example example = new Example(WalletFlow.class);
                    Example.Criteria criteria = example.createCriteria();
                    if (Objects.nonNull(memberWalletFlowQueryReq.getMinAmount())) {
                        criteria.andGreaterThanOrEqualTo("amount", memberWalletFlowQueryReq.getMinAmount());
                    }
                    if (Objects.nonNull(memberWalletFlowQueryReq.getMaxAmount())) {
                        criteria.andLessThanOrEqualTo("amount", memberWalletFlowQueryReq.getMaxAmount());
                    }
                    if (Objects.nonNull(memberWalletFlowQueryReq.getFlowType())) {
                        criteria.andEqualTo("flowType", memberWalletFlowQueryReq.getFlowType());
                    }
                    if (Objects.nonNull(memberWalletFlowQueryReq.getManualChargeType())) {
                        criteria.andEqualTo("manualChargeType", memberWalletFlowQueryReq.getManualChargeType());
                    }
                    if (Objects.nonNull(memberWalletFlowQueryReq.getWalletStartTime())) {
                        criteria.andGreaterThanOrEqualTo("ctime", memberWalletFlowQueryReq.getWalletStartTime());
                    }
                    if (Objects.nonNull(memberWalletFlowQueryReq.getWalletEndTime())) {
                        criteria.andLessThanOrEqualTo("ctime", memberWalletFlowQueryReq.getWalletEndTime());
                    }
                    walletFlowMapper.selectByExample(example);
                });

        List<UserInfoQueryResult> userInfoQueryResults = userInfoCustomMapper.queryUserInfo(memberInfoQuery);
//        Page<UserInfoQueryResult> pageResult1 = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
//                .doSelectPage(() -> userInfoCustomMapper.queryUserInfo(memberInfoQuery));

        resp.setTotal(pageResult.getTotal());

        List<WalletFlow> result = pageResult.getResult();
        if (CollectionUtils.isEmpty(result)) {
            return resp;
        }
//        List<UserInfoQueryResult> userInfoResult = pageResult1.getResult();
//        if (CollectionUtils.isEmpty(userInfoResult)) {
//            return resp;
//        }
        Map<Long, UserInfoQueryResult> userWalletFlowMap = userInfoQueryResults.stream().collect(Collectors.toMap(UserInfoQueryResult::getId, UserInfoQueryResult -> UserInfoQueryResult));

        for (WalletFlow walletFlow : result) {
            if (userWalletFlowMap.containsKey(walletFlow.getUserId())) {
                UserWalletFlowQueryDTO userWalletFlowQueryDTO = new UserWalletFlowQueryDTO();
                UserInfoQueryResult userInfoQueryResult = userWalletFlowMap.get(walletFlow.getUserId());
                BeanUtils.copyProperties(userInfoQueryResult, userWalletFlowQueryDTO);
                userWalletFlowQueryDTO.setAmount(walletFlow.getAmount());
                userWalletFlowQueryDTO.setFlowType(walletFlow.getFlowType());
                userWalletFlowQueryDTO.setManualChargeType(walletFlow.getManualChargeType());
                userWalletFlowQueryDTO.setWalletCtime(walletFlow.getCtime());
                userWalletFlowQueryDTO.setChangeAgent(false);
                if (Objects.nonNull(userInfoQueryResult.getOriginalIdentityTag()) && userInfoQueryResult.getOriginalIdentityTag() == 0
                        && Objects.nonNull(userInfoQueryResult.getCurrentIdentityTag()) && userInfoQueryResult.getCurrentIdentityTag() == 1) {
                    userWalletFlowQueryDTO.setChangeAgent(true);
                }
                userWalletList.add(userWalletFlowQueryDTO);
            }
//            Example example = new Example(WalletFlow.class);
//            Example.Criteria criteria = example.createCriteria();
//            if (Objects.nonNull(memberWalletFlowQueryReq.getMinAmount())){
//                criteria.andGreaterThanOrEqualTo("amount",memberWalletFlowQueryReq.getMinAmount());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getMaxAmount())){
//                criteria.andLessThanOrEqualTo("amount",memberWalletFlowQueryReq.getMaxAmount());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getFlowType())){
//                criteria.andEqualTo("flowType",memberWalletFlowQueryReq.getFlowType());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getManualChargeType())){
//                criteria.andEqualTo("manualChargeType",memberWalletFlowQueryReq.getManualChargeType());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getWalletStartTime())){
//                criteria.andGreaterThanOrEqualTo("ctime",memberWalletFlowQueryReq.getWalletStartTime());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getWalletEndTime())){
//                criteria.andLessThanOrEqualTo("ctime",memberWalletFlowQueryReq.getWalletEndTime());
//            }
//            List<WalletFlow> walletFlows = walletFlowMapper.selectByExample(example);
        }
//        for (UserInfoQueryResult userInfoQueryResult : result) {
//            Example example = new Example(WalletFlow.class);
//            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("userId", userInfoQueryResult.getId());
//            if (Objects.nonNull(memberWalletFlowQueryReq.getMinAmount())){
//                criteria.andGreaterThanOrEqualTo("amount",memberWalletFlowQueryReq.getMinAmount());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getMaxAmount())){
//                criteria.andLessThanOrEqualTo("amount",memberWalletFlowQueryReq.getMaxAmount());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getFlowType())){
//                criteria.andEqualTo("flowType",memberWalletFlowQueryReq.getFlowType());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getManualChargeType())){
//                criteria.andEqualTo("manualChargeType",memberWalletFlowQueryReq.getManualChargeType());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getWalletStartTime())){
//                criteria.andGreaterThanOrEqualTo("ctime",memberWalletFlowQueryReq.getWalletStartTime());
//            }
//            if (Objects.nonNull(memberWalletFlowQueryReq.getWalletEndTime())){
//                criteria.andLessThanOrEqualTo("ctime",memberWalletFlowQueryReq.getWalletEndTime());
//            }
//            List<WalletFlow> walletFlows = walletFlowMapper.selectByExample(example);
//            for (WalletFlow walletFlow : walletFlows) {
//                UserWalletFlowQueryDTO userWalletFlowQueryDTO = new UserWalletFlowQueryDTO();
//                BeanUtils.copyProperties(userInfoQueryResult, userWalletFlowQueryDTO);
//                userWalletFlowQueryDTO.setAmount(walletFlow.getAmount());
//                userWalletFlowQueryDTO.setFlowType(walletFlow.getFlowType());
//                userWalletFlowQueryDTO.setManualChargeType(walletFlow.getManualChargeType());
//                userWalletFlowQueryDTO.setWalletCtime(walletFlow.getCtime());
//
//                userWalletFlowQueryDTO.setChangeAgent(false);
//                if (Objects.nonNull(userInfoQueryResult.getOriginalIdentityTag()) && userInfoQueryResult.getOriginalIdentityTag() == 0
//                        && Objects.nonNull(userInfoQueryResult.getCurrentIdentityTag()) && userInfoQueryResult.getCurrentIdentityTag() == 1) {
//                    userWalletFlowQueryDTO.setChangeAgent(true);
//                }
//                userWalletList.add(userWalletFlowQueryDTO);
//            }
//        }
//        resp.setTotal((long) userWalletList.size());
        resp.setUserWalletList(userWalletList);
        return resp;
    }

    public MemberWalletInfoQueryResp walletInfoQuery(MemberWalletInfoQueryReq memberWalletInfoQueryReq) {
        MemberWalletInfoQueryResp resp = new MemberWalletInfoQueryResp();
        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        BeanUtils.copyProperties(memberWalletInfoQueryReq, memberInfoQuery);
        Paging paging = memberWalletInfoQueryReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }
        Page<UserInfoQueryResult> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit())
                .doSelectPage(() -> userInfoCustomMapper.queryUserInfo(memberInfoQuery));

        List<UserInfoQueryResult> result = pageResult.getResult();
        if (CollectionUtils.isEmpty(result)) {
            return resp;
        }
        List<UserWalletInfoQueryDTO> userWalletInfoList = new ArrayList<>();
        for (UserInfoQueryResult userInfoQueryResult : pageResult) {
            UserWalletInfoQueryDTO userWalletInfoQueryDTO = new UserWalletInfoQueryDTO();
            BeanUtils.copyProperties(userInfoQueryResult, userWalletInfoQueryDTO);

            Example exampleInfo = new Example(WalletInfo.class);
            Example.Criteria criteriaInfo = exampleInfo.createCriteria();
            criteriaInfo.andEqualTo("userId", userInfoQueryResult.getId());

            if (Objects.nonNull(memberWalletInfoQueryReq.getMinCashedAmount())) {
                criteriaInfo.andGreaterThanOrEqualTo("cashedAmount", memberWalletInfoQueryReq.getMinCashedAmount());
            }
            if (Objects.nonNull(memberWalletInfoQueryReq.getMaxCashedAmount())) {
                criteriaInfo.andLessThanOrEqualTo("cashedAmount", memberWalletInfoQueryReq.getMaxCashedAmount());
            }
            if (Objects.nonNull(memberWalletInfoQueryReq.getMinAvailableAmount())) {
                criteriaInfo.andGreaterThanOrEqualTo("availableAmount", memberWalletInfoQueryReq.getMinAvailableAmount());
            }
            if (Objects.nonNull(memberWalletInfoQueryReq.getMaxAvailableAmount())) {
                criteriaInfo.andLessThanOrEqualTo("availableAmount", memberWalletInfoQueryReq.getMaxAvailableAmount());
            }
            WalletInfo walletInfo = walletInfoMapper.selectOneByExample(exampleInfo);
            if (Objects.isNull(walletInfo))
                continue;

            Example exampleFlow = new Example(WalletFlow.class);
            exampleFlow.createCriteria().andEqualTo("userId", userInfoQueryResult.getId());
            List<WalletFlow> walletFlows = walletFlowMapper.selectByExample(exampleFlow);
            BigDecimal manualFee = BigDecimal.ZERO;
            for (WalletFlow walletFlow : walletFlows) {
                //充值费
                if (walletFlow.getFlowType() == WalletFlowTypeEnum.MANUAL.getCode()) {
                    manualFee = manualFee.add(walletFlow.getAmount());
                }
            }
            BigDecimal commissionFee = walletInfo.getTotalAmount().subtract(manualFee);

            if (Objects.nonNull(memberWalletInfoQueryReq.getMinCommisionAmount())) {
                int minCommisionRes = commissionFee.compareTo(memberWalletInfoQueryReq.getMinCommisionAmount());
                if (minCommisionRes == -1) {
                    //总提现小于入参1，或者总提现大于入参2，不符合要求
                    continue;
                }
            }

            if (Objects.nonNull(memberWalletInfoQueryReq.getMaxCommisionAmount())) {
                int maxCommisionRes = commissionFee.compareTo(memberWalletInfoQueryReq.getMaxCommisionAmount());
                if (maxCommisionRes == 1) {
                    //总提现小于入参1，或者总提现大于入参2，不符合要求
                    continue;
                }
            }

            if (Objects.nonNull(memberWalletInfoQueryReq.getMinManualAmount())) {
                int minManualRes = manualFee.compareTo(memberWalletInfoQueryReq.getMinManualAmount());
                if (minManualRes == 1) {
                    //总提现小于入参1，或者总提现大于入参2，不符合要求
                    continue;
                }
            }

            if (Objects.nonNull(memberWalletInfoQueryReq.getMaxManualAmount())) {
                int maxManualRes = manualFee.compareTo(memberWalletInfoQueryReq.getMaxManualAmount());
                if (maxManualRes == 1) {
                    //总提现小于入参1，或者总提现大于入参2，不符合要求
                    continue;
                }
            }

            userWalletInfoQueryDTO.setCommisionAmount(commissionFee);
            userWalletInfoQueryDTO.setAvailableAmount(walletInfo.getAvailableAmount());
            userWalletInfoQueryDTO.setCashedAmount(walletInfo.getCashedAmount());
            userWalletInfoQueryDTO.setManualAmount(manualFee);
            userWalletInfoQueryDTO.setChangeAgent(false);
            if (Objects.nonNull(userInfoQueryResult.getOriginalIdentityTag()) && userInfoQueryResult.getOriginalIdentityTag() == 0
                    && Objects.nonNull(userInfoQueryResult.getCurrentIdentityTag()) && userInfoQueryResult.getCurrentIdentityTag() == 1) {
                userWalletInfoQueryDTO.setChangeAgent(true);
            }
            userWalletInfoList.add(userWalletInfoQueryDTO);
        }

        resp.setTotal((long) userWalletInfoList.size());
        resp.setUserWalletInfoList(userWalletInfoList);

        return resp;
    }
}
