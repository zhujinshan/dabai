package com.dabai.proxy.controller;

import com.dabai.proxy.dto.SignNotifyParam;
import com.dabai.proxy.dto.SucNotifyParam;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 18:45
 */
@Slf4j
@RestController
@RequestMapping("/liness")
@Api(tags = "融富通回调接口")
public class LinessCallbackController {

    @PostMapping("/sign/zx")
    public String zxSignCallback(@RequestBody SignNotifyParam signNotifyParam) {
        return null;
    }

    @PostMapping("/cash/zx")
    public String zxCashCallback(@RequestBody SucNotifyParam sucNotifyParam) {
        return null;
    }
}
