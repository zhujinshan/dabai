package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.encypt.annontation.DecryptApi;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.req.UserPlateformReq;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhangwenzhe03
 * @Date: 2022/6/14 2:23 下午
 */
@Slf4j
@RestController
@RequestMapping("/user/plateform")
@Api(tags = "HBX会员身份变动回调接口")
public class UserPlateformChangeController {

    @Autowired
    private UserInfoFacade userInfoFacade;

    @PostMapping(value = "/change")
    @DecryptApi
    public Result<Boolean> change(@RequestBody UserPlateformReq userPlateformReq) throws Exception {
        userInfoFacade.updateUserPlateformInfo(userPlateformReq);
        return Result.success(Boolean.TRUE);
    }
}
