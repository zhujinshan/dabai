package com.dabai.proxy.controller;

import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.facade.MemberInfoFacade;
import com.dabai.proxy.req.MemberPerformQueryReq;
import com.dabai.proxy.resp.MemberPerformQueryResp;
import com.dabai.proxy.resp.MemberPolicyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:05
 */
@RestController
@RequestMapping("/memberPerform")
@Api(tags = "会员业绩清单")
@Slf4j
public class MemberPerformController {

    @Autowired
    private MemberInfoFacade memberInfoFacade;

    @PostMapping(value = "/pageQuery")
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberPerformQueryResp> pageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberPerformQueryReq memberPerformQueryReq) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = userSession.getOrganizationCode();
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            if (StringUtils.isEmpty(memberPerformQueryReq.getOrganizationCode())) {
                memberPerformQueryReq.setOrganizationCode(organizationCode);
            }
        }
        return Result.success(memberInfoFacade.performQuery(memberPerformQueryReq));
    }

    @GetMapping(value = "/policyList")
    @ApiOperation(value = "业绩详情", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<List<MemberPolicyDTO>> detail(@RequestParam @ApiParam(value = "请求入参", required = true) Long userId) {
        return Result.success(memberInfoFacade.policyList(userId));
    }
}
