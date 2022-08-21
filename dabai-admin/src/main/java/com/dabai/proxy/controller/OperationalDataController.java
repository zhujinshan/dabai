package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.dao.UserActionStatisticsMapper;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.po.UserActionStatistics;
import com.dabai.proxy.req.OperationalDataReq;
import com.dabai.proxy.resp.OperationalDataResp;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/21 00:42
 */
@RestController
@RequestMapping("/operational")
@Api(tags = "运营数据")
@Slf4j
public class OperationalDataController {

    @Resource
    private UserActionStatisticsMapper userActionStatisticsMapper;


    @PostMapping(value = "/query")
    @ApiOperation(value = "运营数据查询", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<List<OperationalDataResp>> query(@RequestBody OperationalDataReq operationalDataReq) {
        if (CollectionUtils.isEmpty(operationalDataReq.getOrganizationCode()) || operationalDataReq.getStartTime() == null || operationalDataReq.getEndTime() == null) {
            return Result.success(Lists.newArrayList());
        }

        Example example = new Example(UserActionStatistics.class);
        example.createCriteria().andIn("organization", operationalDataReq.getOrganizationCode())
                .andBetween("actionDate", operationalDataReq.getStartTime(), operationalDataReq.getEndTime());
        example.setOrderByClause("action_date desc");
        List<UserActionStatistics> userActionStatistics = userActionStatisticsMapper.selectByExample(example);
        List<OperationalDataResp> resps = userActionStatistics.stream().map(e -> {
            OperationalDataResp resp = new OperationalDataResp();
            BeanUtils.copyProperties(e, resp);
            Long t1VisitAmount = e.getT1VisitAmount();
            Long t1RegisterAmount = e.getT1RegisterAmount();
            resp.setT1VisitRate(BigDecimal.ZERO);
            if (t1RegisterAmount != null && t1RegisterAmount > 0) {
                BigDecimal t1RegisterRate = BigDecimal.valueOf(t1VisitAmount).divide(BigDecimal.valueOf(t1RegisterAmount), 2, RoundingMode.HALF_UP);
                resp.setT1VisitRate(t1RegisterRate);
            }
            Long t7VisitAmount = e.getT7VisitAmount();
            Long t7RegisterAmount = e.getT7RegisterAmount();
            resp.setT7VisitRate(BigDecimal.ZERO);
            if (t7RegisterAmount != null && t7RegisterAmount > 0) {
                BigDecimal t7RegisterRate = BigDecimal.valueOf(t7VisitAmount).divide(BigDecimal.valueOf(t7RegisterAmount), 2, RoundingMode.HALF_UP);
                resp.setT7VisitRate(t7RegisterRate);
            }
            Long t30VisitAmount = e.getT30VisitAmount();
            Long t30RegisterAmount = e.getT30RegisterAmount();
            resp.setT30VisitRate(BigDecimal.ZERO);
            if (t7RegisterAmount != null && t7RegisterAmount > 0) {
                BigDecimal t30RegisterRate = BigDecimal.valueOf(t30VisitAmount).divide(BigDecimal.valueOf(t30RegisterAmount), 2, RoundingMode.HALF_UP);
                resp.setT30VisitRate(t30RegisterRate);
            }
            Long dau = e.getDau();
            Long mau = e.getMau();
            Long monthRegisterAmount = e.getMonthRegisterAmount();
            resp.setDauRate(BigDecimal.ZERO);
            resp.setMauRate(BigDecimal.ZERO);
            if (monthRegisterAmount != null && monthRegisterAmount > 0) {
                BigDecimal dauRate = BigDecimal.valueOf(dau).divide(BigDecimal.valueOf(monthRegisterAmount), 2, RoundingMode.HALF_UP);
                resp.setDauRate(dauRate);
                BigDecimal mauRate = BigDecimal.valueOf(mau).divide(BigDecimal.valueOf(monthRegisterAmount), 2, RoundingMode.HALF_UP);
                resp.setMauRate(mauRate);
            }
            resp.setInvitedUsers(convertToList(e.getInvitedUsers()));
            resp.setInvitedAgentUsers(convertToList(e.getInvitedAgentUsers()));
            resp.setInvitedOrderUsers(convertToList(e.getInvitedOrderUsers()));
            resp.setInviteAgentUsers(convertToList(e.getInviteAgentUsers()));
            resp.setInviteUsers(convertToList(e.getInviteUsers()));
            resp.setInviteOrderUsers(convertToList(e.getInviteOrderUsers()));
            resp.setInviteSuccessUsers(convertToList(e.getInviteSuccessUsers()));
            resp.setT7SilentUsers(convertToList(e.getT7SilentUsers()));
            resp.setT30SilentUsers(convertToList(e.getT30SilentUsers()));
            resp.setOldT7SilentUsers(convertToList(e.getOldT7SilentUsers()));
            resp.setOleT30SilentUsers(convertToList(e.getOleT30SilentUsers()));
            return resp;
        }).collect(Collectors.toList());
        return Result.success(resps);
    }

    private List<Long> convertToList(String s) {
        if (StringUtils.isEmpty(s)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(s.split(",")).filter(StringUtils::isNotEmpty).map(Long::parseLong).collect(Collectors.toList());
    }

}
