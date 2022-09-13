package com.dabai.proxy.controller;

import com.dabai.proxy.dao.WxAuditStatusMapper;
import com.dabai.proxy.po.WxAuditStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Author: zhangwenzhe03
 * @Date: 2022/9/13 7:59 下午
 */
@RestController
@RequestMapping("/wxAuditStatus")
@Api(tags = "产品查询接口")
@Slf4j
public class WxAuditStatusController {

    @Resource
    private WxAuditStatusMapper wxAuditStatusMapper;

    /**
     * 只有审核通过是1，其他状态都是0
     * @return
     */
    @GetMapping("/statusQuery")
    @ApiOperation(value = "微信审核状态", httpMethod = "GET")
    public Boolean wxAuditStatusQuery() {
        Example example = new Example(WxAuditStatus.class);
        example.createCriteria();
        WxAuditStatus wxAuditStatus = wxAuditStatusMapper.selectOneByExample(example);
        return (wxAuditStatus.getStatus() == 1) ? true : false;
    }
}
