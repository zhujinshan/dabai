package com.dabai.proxy.controller;

import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.facade.MemberInfoFacade;
import com.dabai.proxy.req.MemberWalletFlowQueryReq;
import com.dabai.proxy.req.MemberWalletInfoQueryReq;
import com.dabai.proxy.resp.MemberWalletFlowQueryResp;
import com.dabai.proxy.resp.MemberWalletInfoQueryResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:05
 */
@RestController
@RequestMapping("/memberWallet")
@Api(tags = "会员收支清单")
@Slf4j
public class MemberWalletController {

    @Autowired
    private MemberInfoFacade memberInfoFacade;

    @PostMapping(value = "/flow/pageQuery")
    @ApiOperation(value = "流水列表", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberWalletFlowQueryResp> flowPageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberWalletFlowQueryReq memberWalletFlowQueryReq) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = userSession.getOrganizationCode();
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            if (StringUtils.isEmpty(memberWalletFlowQueryReq.getOrganizationCode())) {
                memberWalletFlowQueryReq.setOrganizationCode(organizationCode);
            }
        }
        return Result.success(memberInfoFacade.walletFlowQuery(memberWalletFlowQueryReq));
    }

    @PostMapping(value = "/info/pageQuery")
    @ApiOperation(value = "账户列表", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberWalletInfoQueryResp> infoPageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberWalletInfoQueryReq memberWalletInfoQueryReq) {
        AdminUserSessionInfo userSession = AdminUserSessionContext.getAdminUserSessionInfo();
        String organizationCode = userSession.getOrganizationCode();
        if (!userSession.getRole().equals(SysAdminRole.SUPPER_ADMIN)) {
            if (StringUtils.isEmpty(memberWalletInfoQueryReq.getOrganizationCode())) {
                memberWalletInfoQueryReq.setOrganizationCode(organizationCode);
            }
        }
        return Result.success(memberInfoFacade.walletInfoQuery(memberWalletInfoQueryReq));
    }
}