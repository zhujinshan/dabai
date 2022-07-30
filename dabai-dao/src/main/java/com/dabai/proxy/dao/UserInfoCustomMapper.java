package com.dabai.proxy.dao;

import com.dabai.proxy.po.UserInfoQueryResult;
import com.dabai.proxy.query.MemberInfoQuery;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:59
 */
public interface UserInfoCustomMapper {

    List<UserInfoQueryResult> queryUserInfo(MemberInfoQuery memberInfoQuery);

}
