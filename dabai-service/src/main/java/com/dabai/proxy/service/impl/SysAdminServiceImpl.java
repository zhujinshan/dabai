package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.SysAdminMapper;
import com.dabai.proxy.enums.SysAdminStatus;
import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.service.SysAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 16:53
 */
@Service
@Slf4j
public class SysAdminServiceImpl implements SysAdminService {

    @Resource
    private SysAdminMapper sysAdminMapper;

    @Override
    public SysAdmin getByMobile(String mobile) {
        Assert.notNull(mobile, "手机号不能为空");

        Example example = new Example(SysAdmin.class);
        example.createCriteria().andEqualTo("mobile", mobile)
                .andEqualTo("status", SysAdminStatus.NORMAL.getCode());
        return sysAdminMapper.selectOneByExample(example);
    }
}
