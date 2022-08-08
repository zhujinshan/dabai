package com.dabai.proxy.controller;

import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.dao.SysStatisticsMapper;
import com.dabai.proxy.dao.UserPlateformInfoMapper;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.resp.StatisticsAgentChangeResp;
import com.dabai.proxy.resp.StatisticsCashResp;
import com.dabai.proxy.resp.StatisticsPolicyResp;
import com.dabai.proxy.resp.StatisticsRealNameResp;
import com.dabai.proxy.resp.StatisticsRegisterResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 10:35
 */
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计概览")
@Slf4j
public class StatisticsController {

    @Resource
    private UserPlateformInfoMapper userPlateformInfoMapper;

    @Resource
    private SysStatisticsMapper sysStatisticsMapper;

    @GetMapping(value = "/register")
    @ApiOperation(value = "注册概览", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<StatisticsRegisterResp> register() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = null;
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            organizationCode = userSession.getOrganizationCode();
        }

        Example example = new Example(UserPlateformInfo.class);
        if(StringUtils.isNotEmpty(organizationCode)) {
            example.createCriteria().andEqualTo("organizationCode", organizationCode);
        }
        int total = userPlateformInfoMapper.selectCountByExample(example);

        Pair<Date, Date> yesterDay = getDate(1);
        Pair<Date, Date> monthDay = getDate(31);
        Example example2 = new Example(UserPlateformInfo.class);
        Example.Criteria criteria2 = example2.createCriteria();
        if(StringUtils.isNotEmpty(organizationCode)) {
            criteria2.andLike("organizationCode", organizationCode + "%");
        }
        criteria2.andBetween("ctime", yesterDay.getLeft(), yesterDay.getRight());
        int yesterDayCount = userPlateformInfoMapper.selectCountByExample(example2);

        Example example3 = new Example(UserPlateformInfo.class);
        Example.Criteria criteria3 = example3.createCriteria();
        if(StringUtils.isNotEmpty(organizationCode)) {
            criteria3.andEqualTo("organizationCode", organizationCode);
        }
        criteria3.andBetween("ctime", monthDay.getLeft(), monthDay.getRight());
        int monthCount = userPlateformInfoMapper.selectCountByExample(example3);
        StatisticsRegisterResp resp = new StatisticsRegisterResp();
        resp.setTotalAmount(total);
        resp.setYesterdayAmount(yesterDayCount);
        resp.setMonthAmount(monthCount);
        return Result.success(resp);
    }

    @GetMapping(value = "/realName")
    @ApiOperation(value = "实名认证概览", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<StatisticsRealNameResp> realName() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = null;
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            organizationCode = userSession.getOrganizationCode();
        }

        Pair<Date, Date> yesterDay = getDate(1);
        Pair<Date, Date> monthDay = getDate(31);

        long totalCount = sysStatisticsMapper.realName(organizationCode, null, null);
        long yesterCount = sysStatisticsMapper.realName(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        long monthAmount = sysStatisticsMapper.realName(organizationCode, monthDay.getLeft(), monthDay.getRight());

        StatisticsRealNameResp resp = new StatisticsRealNameResp();
        resp.setTotalAmount(totalCount);
        resp.setYesterdayAmount(yesterCount);
        resp.setMonthAmount(monthAmount);
        return Result.success(resp);
    }

    @GetMapping(value = "/policy")
    @ApiOperation(value = "会员业绩概览", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<StatisticsPolicyResp> policy() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = null;
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            organizationCode = userSession.getOrganizationCode();
        }

        Pair<Date, Date> yesterDay = getDate(1);
        Pair<Date, Date> monthDay = getDate(31);

        long totalMemeberCount = sysStatisticsMapper.policyMemeberCount(organizationCode, null, null);
        long yesterMemeberCount = sysStatisticsMapper.policyMemeberCount(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        long monthMemeberCount = sysStatisticsMapper.policyMemeberCount(organizationCode, monthDay.getLeft(), monthDay.getRight());
        BigDecimal totalAmount = sysStatisticsMapper.policyAmount(organizationCode, null, null);
        BigDecimal yesterAmount = sysStatisticsMapper.policyAmount(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        BigDecimal monthAmount = sysStatisticsMapper.policyAmount(organizationCode, monthDay.getLeft(), monthDay.getRight());

        StatisticsPolicyResp resp = new StatisticsPolicyResp();
        resp.setTotalMemberAmount(totalMemeberCount);
        resp.setYesterdayMemberAmount(yesterMemeberCount);
        resp.setMonthMemberAmount(monthMemeberCount);
        resp.setTotalPolicyAmount(totalAmount);
        resp.setYesterdayPolicyAmount(yesterAmount);
        resp.setMonthPolicyAmount(monthAmount);
        return Result.success(resp);
    }

    @GetMapping(value = "/cash")
    @ApiOperation(value = "会员提现概览", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<StatisticsCashResp> cash() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = null;
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            organizationCode = userSession.getOrganizationCode();
        }

        Pair<Date, Date> yesterDay = getDate(1);
        Pair<Date, Date> monthDay = getDate(31);

        long totalMemeberCount = sysStatisticsMapper.cashMemberAmount(organizationCode, null, null);
        long yesterMemeberCount = sysStatisticsMapper.cashMemberAmount(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        long monthMemeberCount = sysStatisticsMapper.cashMemberAmount(organizationCode, monthDay.getLeft(), monthDay.getRight());
        BigDecimal totalAmount = sysStatisticsMapper.cashAmount(organizationCode, null, null);
        BigDecimal yesterAmount = sysStatisticsMapper.cashAmount(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        BigDecimal monthAmount = sysStatisticsMapper.cashAmount(organizationCode, monthDay.getLeft(), monthDay.getRight());

        StatisticsCashResp resp = new StatisticsCashResp();
        resp.setTotalMemberAmount(totalMemeberCount);
        resp.setYesterdayMemberAmount(yesterMemeberCount);
        resp.setMonthMemberAmount(monthMemeberCount);
        resp.setTotalCashedAmount(totalAmount);
        resp.setYesterdayCashedAmount(yesterAmount);
        resp.setMonthCashedAmount(monthAmount);
        return Result.success(resp);
    }

    @GetMapping(value = "/agentChange")
    @ApiOperation(value = "会员转化代理人概览", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<StatisticsAgentChangeResp> agentChange() {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = null;
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            organizationCode = userSession.getOrganizationCode();
        }

        Pair<Date, Date> yesterDay = getDate(1);
        Pair<Date, Date> monthDay = getDate(31);

        long totalMemeberCount = sysStatisticsMapper.agentChangeAmount(organizationCode, null, null);
        long yesterMemeberCount = sysStatisticsMapper.agentChangeAmount(organizationCode, yesterDay.getLeft(), yesterDay.getRight());
        long monthMemeberCount = sysStatisticsMapper.agentChangeAmount(organizationCode, monthDay.getLeft(), monthDay.getRight());

        StatisticsAgentChangeResp resp = new StatisticsAgentChangeResp();
        resp.setTotalMemberAmount(totalMemeberCount);
        resp.setYesterdayMemberAmount(yesterMemeberCount);
        resp.setMonthMemberAmount(monthMemeberCount);
        return Result.success(resp);
    }

    private Pair<Date, Date> getDate(Integer days) {
        LocalDateTime yesterDayBegin = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        LocalDateTime yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
        Date startDate = Date.from(yesterDayBegin.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(yesterDayEnd.atZone(ZoneId.systemDefault()).toInstant());
        return Pair.of(startDate, endDate);
    }


}
