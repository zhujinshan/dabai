package com.dabai.proxy.service;

import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.query.SysAdminQuery;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 16:51
 */
public interface SysAdminService {

    SysAdmin getById(Long userId);

    SysAdmin getByMobile(String mobile);

    int add(SysAdmin sysAdmin);

    void disabled(Long userId, Long updateUser);

    List<SysAdmin> query(SysAdminQuery sysAdminQuery);

    void update(SysAdmin currentAdmin);
}
