package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.SysAdminMapper;
import com.dabai.proxy.enums.SysAdminStatus;
import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.query.SysAdminQuery;
import com.dabai.proxy.service.SysAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public SysAdmin getById(Long userId) {
        Assert.notNull(userId, "userId不能为空");
        return sysAdminMapper.selectByPrimaryKey(userId);
    }

    @Override
    public SysAdmin getByMobile(String mobile) {
        Assert.notNull(mobile, "手机号不能为空");

        Example example = new Example(SysAdmin.class);
        example.createCriteria().andEqualTo("mobile", mobile)
                .andEqualTo("status", SysAdminStatus.NORMAL.getCode());
        return sysAdminMapper.selectOneByExample(example);
    }

    @Override
    public int add(SysAdmin sysAdmin) {
        Assert.notNull(sysAdmin, "账号信息不能为空");
        sysAdmin.setCtime(new Date());
        sysAdmin.setUtime(new Date());
//        sysAdmin.setStatus(SysAdminStatus.NORMAL.getCode());
        return sysAdminMapper.insertSelective(sysAdmin);
    }

    @Override
    public void disabled(Long userId, Long updateUser) {
        Assert.notNull(userId, "userId不能为空");
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setId(userId);
        sysAdmin.setStatus(SysAdminStatus.DISABLE.getCode());
        sysAdmin.setUpdateUserId(updateUser);
        sysAdmin.setUtime(new Date());
        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
    }

    @Override
    public List<SysAdmin> query(SysAdminQuery sysAdminQuery) {
        if (sysAdminQuery == null) {
            sysAdminQuery = new SysAdminQuery();
        }
        Example example = new Example(SysAdmin.class);
        example.setOrderByClause("status IN (0, 4) DESC");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(sysAdminQuery.getMobile())) {
            criteria.andEqualTo("mobile", sysAdminQuery.getMobile());
        }
        if (sysAdminQuery.getRole() != null) {
            criteria.andEqualTo("role", sysAdminQuery.getRole());
        }
        if (sysAdminQuery.getCreateUserId() != null) {
            criteria.andEqualTo("createUserId", sysAdminQuery.getCreateUserId());
        }
        return sysAdminMapper.selectByExample(example);
    }

    @Override
    public void update(SysAdmin currentAdmin) {
        Assert.notNull(currentAdmin, "未知用户");
        Assert.notNull(currentAdmin.getId(), "id is required");
        currentAdmin.setUtime(new Date());
        sysAdminMapper.updateByPrimaryKeySelective(currentAdmin);
    }
}
