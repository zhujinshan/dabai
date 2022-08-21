package com.dabai.proxy.task;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dao.UserActionCustomMapper;
import com.dabai.proxy.dao.UserActionMapper;
import com.dabai.proxy.dao.UserActionStatisticsMapper;
import com.dabai.proxy.dao.UserInfoCustomMapper;
import com.dabai.proxy.dao.UserInfoMapper;
import com.dabai.proxy.dao.UserPlateformInfoMapper;
import com.dabai.proxy.dao.WalletFlowMapper;
import com.dabai.proxy.enums.HbxUserTag;
import com.dabai.proxy.enums.UserActionEnum;
import com.dabai.proxy.enums.WalletFlowTypeEnum;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.UserAction;
import com.dabai.proxy.po.UserActionStatistics;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserInfoQueryResult;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.po.WalletFlow;
import com.dabai.proxy.query.MemberInfoQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/20 21:07
 */
@Component
public class UserActionTask {

    private static final Map<String, String> ORG_MAP = Maps.newHashMap();

    static {
        ORG_MAP.put("00", "总公司");
        ORG_MAP.put("11", "北京分公司");
        ORG_MAP.put("62", "甘肃分公司");
        ORG_MAP.put("44", "广东分公司");
        ORG_MAP.put("45", "广西分公司");
        ORG_MAP.put("13", "河北分公司");
        ORG_MAP.put("41", "河南分公司");
        ORG_MAP.put("14", "江苏分公司");
        ORG_MAP.put("51", "四川分公司");
        ORG_MAP.put("33", "浙江分公司");
        ORG_MAP.put("32", "江苏分公司");
    }

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoCustomMapper userInfoCustomMapper;

    @Resource
    private UserActionStatisticsMapper userActionStatisticsMapper;

    @Resource
    private UserActionMapper userActionMapper;

    @Resource
    private UserActionCustomMapper userActionCustomMapper;

    @Resource
    private PolicyInfoMapper policyInfoMapper;

    @Resource
    private UserPlateformInfoMapper userPlateformInfoMapper;

    @Resource
    private WalletFlowMapper walletFlowMapper;

    @Scheduled(cron = "0 10 0 * * ?")
    @Transactional(rollbackFor = Throwable.class)
    public void userActionTask() {
        Date t = getDateInfo(1);
        Example example = new Example(UserActionStatistics.class);
        example.createCriteria().andEqualTo("actionDate", t);
        userActionStatisticsMapper.deleteByExample(example);
        for (String org : ORG_MAP.keySet()) {
            orgAction(t, org);
        }
    }

    private void orgAction(Date t, String org) {
        UserActionStatistics userActionStatistics = new UserActionStatistics();
        userActionStatistics.setOrganization(org);
        userActionStatistics.setActionDate(t);
        userActionStatistics.setCtime(new Date());

        Pair<Date, Date> tDate = getDate(1);
        List<UserInfoQueryResult> tRegisterUserInfos = registerCount(tDate.getLeft(), tDate.getRight(), org);
        userActionStatistics.settRegisterAmount((long) tRegisterUserInfos.size());

        Pair<Date, Date> t1Date = getDate(2);
        List<UserInfoQueryResult> t1RegisterUserInfos = registerCount(t1Date.getLeft(), t1Date.getRight(), org);
        userActionStatistics.setT1RegisterAmount((long) t1RegisterUserInfos.size());

        Pair<Date, Date> t7Date = getDate(8);
        List<UserInfoQueryResult> t7RegisterUserInfos = registerCount(t7Date.getLeft(), t7Date.getRight(), org);
        userActionStatistics.setT7RegisterAmount((long) t7RegisterUserInfos.size());

        Pair<Date, Date> t30Date = getDate(31);
        List<UserInfoQueryResult> t30RegisterUserInfos = registerCount(t30Date.getLeft(), t30Date.getRight(), org);
        userActionStatistics.setT30RegisterAmount((long) t30RegisterUserInfos.size());

        LocalDateTime st = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.MIN);
        Date from = Date.from(st.atZone(ZoneId.systemDefault()).toInstant());
        List<UserInfoQueryResult> m30RegisterUserInfos = registerCount(from, tDate.getRight(), org);
        userActionStatistics.setMonthRegisterAmount((long) m30RegisterUserInfos.size());

        userActionStatistics.setT1VisitAmount(0L);
        if (!CollectionUtils.isEmpty(t1RegisterUserInfos)) {
            List<Long> t1UserIds = t1RegisterUserInfos.stream().map(UserInfoQueryResult::getId).collect(Collectors.toList());
            Long t1Visit = visitCount(t1UserIds, tDate.getLeft(), tDate.getRight());
            userActionStatistics.setT1VisitAmount(t1Visit);
        }

        userActionStatistics.setT7VisitAmount(0L);
        userActionStatistics.setT7SilentUsers("");
        if (!CollectionUtils.isEmpty(t7RegisterUserInfos)) {
            List<Long> t7UserIds = t7RegisterUserInfos.stream().map(UserInfoQueryResult::getId).collect(Collectors.toList());
            Long t7Visit = visitCount(t7UserIds, tDate.getLeft(), tDate.getRight());
            userActionStatistics.setT7VisitAmount(t7Visit);

            Pair<Date, Date> t7TDate = getTDate(7);
            List<UserAction> userActions = visitList(t7UserIds, t7TDate.getLeft(), t7TDate.getRight());
            Set<Long> existedUsers = userActions.stream().map(UserAction::getUserId).collect(Collectors.toSet());
            List<Long> notExistUsers = userActions.stream().map(UserAction::getUserId).filter(userId -> !existedUsers.contains(userId)).collect(Collectors.toList());
            userActionStatistics.setT7SilentUsers(notExistUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        userActionStatistics.setT30VisitAmount(0L);
        userActionStatistics.setT30SilentUsers("");
        if (!CollectionUtils.isEmpty(t30RegisterUserInfos)) {
            List<Long> t30UserIds = t30RegisterUserInfos.stream().map(UserInfoQueryResult::getId).collect(Collectors.toList());
            Long t30Visit = visitCount(t30UserIds, tDate.getLeft(), tDate.getRight());
            userActionStatistics.setT30VisitAmount(t30Visit);

            Pair<Date, Date> t30TDate = getTDate(30);
            List<UserAction> userActions = visitList(t30UserIds, t30TDate.getLeft(), t30TDate.getRight());
            Set<Long> existedUsers = userActions.stream().map(UserAction::getUserId).collect(Collectors.toSet());
            List<Long> notExistUsers = userActions.stream().map(UserAction::getUserId).filter(userId -> !existedUsers.contains(userId)).collect(Collectors.toList());
            userActionStatistics.setT30SilentUsers(notExistUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        Long dau = actionCount(org, tDate.getLeft(), tDate.getRight(), UserActionEnum.VISIT);
        userActionStatistics.setDau(dau);
        Long mau = actionCount(org, t30Date.getLeft(), t30Date.getRight(), UserActionEnum.VISIT);
        userActionStatistics.setMau(mau);

        List<UserAction> userActions = actionList(org, tDate.getLeft(), tDate.getRight(), UserActionEnum.INVITE);
        List<Long> userIds = userActions.stream().map(UserAction::getUserId).distinct().collect(Collectors.toList());
        userActionStatistics.setInviteUsers("");
        userActionStatistics.setInviteAgentUsers("");
        userActionStatistics.setInviteOrderUsers("");
        userActionStatistics.setInviteSuccessUsers("");
        userActionStatistics.setInvitedUsers("");
        userActionStatistics.setInvitedOrderUsers("");
        userActionStatistics.setInvitedAgentUsers("");

        if (!CollectionUtils.isEmpty(userIds)) {
            userActionStatistics.setInviteUsers(userIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
            List<UserInfo> userInfos = queryByInvitorAndDate(userIds, tDate.getLeft(), tDate.getRight());
            if (!CollectionUtils.isEmpty(userInfos)) {
                List<Long> inviteSuccessUsers = userInfos.stream().map(UserInfo::getParentUserId).distinct().collect(Collectors.toList());
                userActionStatistics.setInviteSuccessUsers(inviteSuccessUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));

                List<Long> invitedScUsers = userInfos.stream().map(UserInfo::getId).collect(Collectors.toList());
                userActionStatistics.setInvitedUsers(invitedScUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
                List<Long> invitedOrUsers = queryOrderUsersByInvitorAndDate(invitedScUsers, tDate.getLeft(), tDate.getRight());
                if (!CollectionUtils.isEmpty(invitedOrUsers)) {
                    List<Long> inviteOrderUsers = userInfos.stream().filter(e -> invitedOrUsers.contains(e.getId())).map(UserInfo::getParentUserId).distinct().collect(Collectors.toList());
                    userActionStatistics.setInviteOrderUsers(inviteOrderUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
                    userActionStatistics.setInvitedOrderUsers(invitedOrUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
                }

                List<Long> invitedAgentUsers = queryAgentUserByInvitorAndDate(invitedScUsers, tDate.getLeft(), tDate.getRight());
                if (CollectionUtils.isEmpty(invitedAgentUsers)) {
                    List<Long> inviteAgentUsers = userInfos.stream().filter(e -> invitedAgentUsers.contains(e.getId())).map(UserInfo::getParentUserId).distinct().collect(Collectors.toList());
                    userActionStatistics.setInviteAgentUsers(inviteAgentUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
                    userActionStatistics.setInvitedAgentUsers(invitedAgentUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
                }
            }
        }

        List<Long> t7LoginUsers = userActionCustomMapper.selectLoginUser(org, t7Date.getLeft(), t7Date.getRight(), null);
        userActionStatistics.setOldT7SilentUsers("");
        if (!CollectionUtils.isEmpty(t7LoginUsers)) {
            List<Long> t7OrderOrWithdrawUsers = historyOrderOrWithdrawalUsers(t7LoginUsers, t7Date.getRight());
            List<Long> t7ExistLoginUsers = userActionCustomMapper.selectLoginUser(org, t7Date.getRight(), tDate.getRight(), t7OrderOrWithdrawUsers);
            List<Long> t7NoLoginUsers = t7OrderOrWithdrawUsers.stream().filter(e -> !t7ExistLoginUsers.contains(e)).collect(Collectors.toList());
            userActionStatistics.setOldT7SilentUsers(t7NoLoginUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        List<Long> t30LoginUsers = userActionCustomMapper.selectLoginUser(org, t30Date.getLeft(), t30Date.getRight(), null);
        userActionStatistics.setOleT30SilentUsers("");
        if (!CollectionUtils.isEmpty(t30LoginUsers)) {
            List<Long> t30OrderOrWithdrawUsers = historyOrderOrWithdrawalUsers(t30LoginUsers, t30Date.getRight());
            List<Long> t30ExistLoginUsers = userActionCustomMapper.selectLoginUser(org, t30Date.getRight(), tDate.getRight(), t30OrderOrWithdrawUsers);
            List<Long> t30NoLoginUsers = t30OrderOrWithdrawUsers.stream().filter(e -> !t30ExistLoginUsers.contains(e)).collect(Collectors.toList());
            userActionStatistics.setOleT30SilentUsers(t30NoLoginUsers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        userActionStatisticsMapper.insertSelective(userActionStatistics);
    }


    public List<Long> historyOrderOrWithdrawalUsers(List<Long> userIds, Date endTime) {
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andIn("userId", userIds)
                .andLessThan("ctime", endTime);
        List<PolicyInfo> policyInfos = policyInfoMapper.selectByExample(example);
        Set<Long> policyedUsers = policyInfos.stream().map(PolicyInfo::getUserId).collect(Collectors.toSet());

        Example example2 = new Example(WalletFlow.class);
        example2.createCriteria().andIn("userId", userIds)
                .andEqualTo("flowType", WalletFlowTypeEnum.WITHDRAWAL.getCode())
                .andLessThan("ctime", endTime);

        List<WalletFlow> walletFlows = walletFlowMapper.selectByExample(example2);
        Set<Long> withdrawalUsers = walletFlows.stream().map(WalletFlow::getUserId).collect(Collectors.toSet());

        policyedUsers.addAll(withdrawalUsers);
        return Lists.newArrayList(policyedUsers);
    }


    private List<Long> queryAgentUserByInvitorAndDate(List<Long> users, Date startTime, Date endTime) {
        Example example = new Example(UserPlateformInfo.class);
        example.createCriteria().andEqualTo("identityTag", HbxUserTag.AGENT.getCode().byteValue())
                .andIn("userId", users)
                .andBetween("ctime", startTime, endTime);

        List<UserPlateformInfo> userPlateformInfos = userPlateformInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userPlateformInfos)) {
            return Lists.newArrayList();
        }
        return userPlateformInfos.stream().map(UserPlateformInfo::getUserId).distinct().collect(Collectors.toList());
    }

    private List<Long> queryOrderUsersByInvitorAndDate(List<Long> users, Date startTime, Date endTime) {
        Example example = new Example(PolicyInfo.class);
        example.createCriteria().andIn("userId", users)
                .andBetween("ctime", startTime, endTime);
        List<PolicyInfo> policyInfos = policyInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(policyInfos)) {
            return Lists.newArrayList();
        }
        return policyInfos.stream().map(PolicyInfo::getUserId).distinct().collect(Collectors.toList());
    }


    private List<UserInfo> queryByInvitorAndDate(List<Long> invitors, Date startTime, Date endTime) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andIn("parentUserId", invitors)
                .andBetween("ctime", startTime, endTime);
        return userInfoMapper.selectByExample(example);
    }

    private Long visitCount(List<Long> userIds, Date startTime, Date endTime) {
        Example example = new Example(UserAction.class);
        example.createCriteria().andBetween("ctime", startTime, endTime)
                .andEqualTo("action", UserActionEnum.VISIT.getCode())
                .andIn("userId", userIds);

        List<UserAction> userActions = userActionMapper.selectByExample(example);
        return userActions.stream().map(UserAction::getUserId).distinct().count();
    }

    private List<UserAction> visitList(List<Long> userIds, Date startTime, Date endTime) {
        Example example = new Example(UserAction.class);
        example.createCriteria().andBetween("ctime", startTime, endTime)
                .andEqualTo("action", UserActionEnum.VISIT.getCode())
                .andIn("userId", userIds);

        return userActionMapper.selectByExample(example);
    }

    private Long actionCount(String org, Date startTime, Date endTime, UserActionEnum userAction) {
        List<UserAction> userActions = userActionCustomMapper.selectByOrgAndDate(org, startTime, endTime, userAction.getCode());
        return userActions.stream().map(UserAction::getUserId).distinct().count();
    }

    private List<UserAction> actionList(String org, Date startTime, Date endTime, UserActionEnum userAction) {
        return userActionCustomMapper.selectByOrgAndDate(org, startTime, endTime, userAction.getCode());
    }

    private List<UserInfoQueryResult> registerCount(Date startTime, Date endTime, String org) {
        MemberInfoQuery memberInfoQuery = new MemberInfoQuery();
        memberInfoQuery.setRegisterStartTime(startTime);
        memberInfoQuery.setRegisterEndTime(endTime);
        memberInfoQuery.setOrganizationCode(org);
        return userInfoCustomMapper.queryUserInfo(memberInfoQuery);
    }


    private Pair<Date, Date> getDate(Integer days) {
        LocalDateTime yesterDayBegin = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        LocalDateTime yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MAX);
        Date startDate = Date.from(yesterDayBegin.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(yesterDayEnd.atZone(ZoneId.systemDefault()).toInstant());
        return Pair.of(startDate, endDate);
    }

    private Pair<Date, Date> getTDate(Integer days) {
        LocalDateTime yesterDayBegin = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        LocalDateTime yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
        Date startDate = Date.from(yesterDayBegin.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(yesterDayEnd.atZone(ZoneId.systemDefault()).toInstant());
        return Pair.of(startDate, endDate);
    }

    private Date getDateInfo(Integer days) {
        LocalDateTime yesterDayBegin = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        return Date.from(yesterDayBegin.atZone(ZoneId.systemDefault()).toInstant());
    }

}