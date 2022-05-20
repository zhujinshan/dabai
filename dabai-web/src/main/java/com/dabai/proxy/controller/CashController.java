package com.dabai.proxy.controller;

import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.facade.CashFacade;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.req.PolicyInfoPageReq;
import com.dabai.proxy.req.UserCashSubmitReq;
import com.dabai.proxy.req.UserSignReq;
import com.dabai.proxy.resp.CashInfoPageResult;
import com.dabai.proxy.resp.PolicyInfoPageResult;
import com.dabai.proxy.resp.PolicyInfoResp;
import com.dabai.proxy.resp.UserCashSignInfoResp;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提现接口
 *
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:32
 */
@RestController
@RequestMapping("/cash")
@Api(tags = "提现接口")
@Slf4j
public class CashController {

    @Resource
    private CashFacade cashFacade;

    @GetMapping("/signInfo")
    @CheckToken
    @ApiOperation(value = "获取提现信息", httpMethod = "GET")
    public Result<UserCashSignInfoResp> signInfo() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        return Result.success(cashFacade.cashInfo(sessionInfo.getOpenId()));
    }

    @PostMapping("/sign")
    @CheckToken
    @ApiOperation(value = "签约", httpMethod = "POST")
    public Result<UserCashSignInfoResp> sign(@ApiParam(value = "签约信息", required = true) @RequestBody UserSignReq userSignReq) {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        return Result.success(cashFacade.sign(sessionInfo.getOpenId(), userSignReq));
    }


    @PostMapping("/submit")
    @CheckToken
    @ApiOperation(value = "打款提现", httpMethod = "POST")
    public Result<String> submit(@ApiParam(value = "打款提现", required = true) @RequestBody UserCashSubmitReq cashSubmitReq) {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        Assert.notNull(cashSubmitReq.getCode(), "验证码不能为空");
        Assert.notNull(cashSubmitReq.getMobile(), "手机号不能为空");
        Assert.isTrue(LocalCache.checkCode(cashSubmitReq.getMobile(), cashSubmitReq.getCode()), "验证码无效，请重新获取");

        return cashFacade.cashSubmit(sessionInfo.getOpenId(), cashSubmitReq);
    }

    @PostMapping("/pageQuery")
    @CheckToken
    @ApiOperation(value = "提现列表", httpMethod = "POST")
    public Result<CashInfoPageResult> pageQuery(@RequestBody Paging paging) {
        if (paging == null) {
            paging = new Paging();
        }
        return Result.success(cashFacade.pageQuery(paging));
    }
}
