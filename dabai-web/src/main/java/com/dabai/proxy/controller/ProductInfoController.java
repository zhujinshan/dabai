package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.facade.ProductInfoFacade;
import com.dabai.proxy.po.ProductInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:07
 */
@RestController
@RequestMapping("/productInfo")
@Api(tags = "产品查询接口")
@Slf4j
public class ProductInfoController {

    @Resource
    private ProductInfoFacade productInfoFacade;

    @GetMapping("/pageQuery")
    @ApiOperation(value = "产品列表", httpMethod = "GET")
    public Result<List<ProductInfo>> pageQuery() {
        return Result.success(productInfoFacade.pageQuery());
    }
}
