package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.dao.ProductInfoMapper;
import com.dabai.proxy.facade.ProductInfoFacade;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.req.Paging;
import com.dabai.proxy.req.PolicyInfoPageReq;
import com.dabai.proxy.resp.PolicyInfoResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

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
    @CheckToken
    @ApiOperation(value = "产品列表", httpMethod = "GET")
    public Result<List<ProductInfo>> pageQuery() {
        return Result.success(productInfoFacade.pageQuery());
    }
}
