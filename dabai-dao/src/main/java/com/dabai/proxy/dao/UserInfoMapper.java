package com.dabai.proxy.dao;

import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserInfoMapper extends Mapper<UserInfo> {
    long countByExample(UserInfoExample example);

    List<UserInfo> selectByExample(UserInfoExample example);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);
}