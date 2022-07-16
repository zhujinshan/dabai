package com.dabai.proxy.service;

import com.dabai.proxy.po.SysAdmin;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 16:51
 */
public interface SysAdminService {

    SysAdmin getByMobile(String mobile);
}
