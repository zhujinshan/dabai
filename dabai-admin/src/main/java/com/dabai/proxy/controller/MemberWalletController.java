package com.dabai.proxy.controller;

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
    //@PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberWalletFlowQueryResp> flowPageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberWalletFlowQueryReq memberWalletFlowQueryReq) {
        return Result.success(memberInfoFacade.walletFlowQuery(memberWalletFlowQueryReq));
    }

    @PostMapping(value = "/info/pageQuery")
    @ApiOperation(value = "账户列表", httpMethod = "POST")
    //@PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberWalletInfoQueryResp> infoPageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberWalletInfoQueryReq memberWalletInfoQueryReq) {
        return Result.success(memberInfoFacade.walletInfoQuery(memberWalletInfoQueryReq));
    }
}
