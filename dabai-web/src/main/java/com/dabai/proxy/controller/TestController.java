package com.dabai.proxy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/4 12:05
 */
@RestController
@Slf4j
@RequestMapping("/")
@Api(tags = "测试接口")
public class TestController {

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", httpMethod = "GET")
    public String test() {
        return "ok";
    }
}
