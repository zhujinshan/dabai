package com.dabai.proxy.dao;

import com.dabai.proxy.po.UserSignInfo;
import com.dabai.proxy.po.UserSignInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserSignInfoMapper extends Mapper<UserSignInfo> {
    long countByExample(UserSignInfoExample example);

    List<UserSignInfo> selectByExample(UserSignInfoExample example);

    int updateByExampleSelective(@Param("record") UserSignInfo record, @Param("example") UserSignInfoExample example);

    int updateByExample(@Param("record") UserSignInfo record, @Param("example") UserSignInfoExample example);
}