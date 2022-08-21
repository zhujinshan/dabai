package com.dabai.proxy.dao;

import com.dabai.proxy.po.UserAction;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserActionCustomMapper {

    List<UserAction> selectByOrgAndDate(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("action") Integer action);

    List<Long> selectLoginUser(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("userIds") List<Long> userIds);
}