package com.dabai.proxy.controller;

import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.req.PolicyInfoPageReq;
import com.dabai.proxy.resp.PolicyInfoPageResult;
import com.dabai.proxy.resp.PolicyInfoResp;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.UserInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:07
 */
@RestController
@RequestMapping("/policyInfo")
@Api(tags = "保单查询接口")
@Slf4j
public class PolicyInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PolicyInfoService policyInfoService;

    @PostMapping("/pageQuery")
    @CheckToken
    @ApiOperation(value = "保单列表", httpMethod = "POST")
    public Result<PolicyInfoPageResult> pageQuery(@RequestBody PolicyInfoPageReq policyInfoPageReq) {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        UserInfo userInfo = userInfoService.selectByOpenId(sessionInfo.getOpenId());
        Assert.notNull(userInfo, "用户无效，请重新登录");

        Paging paging = policyInfoPageReq.getPaging();
        if (paging == null) {
            paging = new Paging();
        }

        Page<PolicyInfo> pageResult = PageHelper.offsetPage(paging.getOffset(), paging.getLimit()).doSelectPage(() -> policyInfoService.pageQuery(userInfo.getId(), policyInfoPageReq.getStatus(), policyInfoPageReq.getPolicyNo()));
        PolicyInfoPageResult resp = new PolicyInfoPageResult();
        resp.setTotal(pageResult.getTotal());
        List<PolicyInfo> result = pageResult.getResult();
        List<PolicyInfoResp> policyInfoRespList = result.stream().map(e -> {
            PolicyInfoResp policyInfoResp = new PolicyInfoResp();
            BeanUtils.copyProperties(e, policyInfoResp);
            return policyInfoResp;
        }).collect(Collectors.toList());
        resp.setList(policyInfoRespList);
        return Result.success(resp);
    }
}
