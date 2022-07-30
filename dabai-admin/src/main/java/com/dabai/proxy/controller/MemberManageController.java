package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.facade.MemberInfoFacade;
import com.dabai.proxy.req.MemberInfoQueryReq;
import com.dabai.proxy.resp.MemberInfoQueryResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:05
 */
@RestController
@RequestMapping("/memberInfo")
@Api(tags = "会员人员清单")
@Slf4j
public class MemberManageController {

    @Autowired
    private MemberInfoFacade memberInfoFacade;

    @PostMapping(value = "/pageQuery")
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<MemberInfoQueryResp> pageQuery(@RequestBody @ApiParam(value = "请求入参", required = true) MemberInfoQueryReq memberInfoQueryReq) {
        return Result.success(memberInfoFacade.query(memberInfoQueryReq));
    }
}
