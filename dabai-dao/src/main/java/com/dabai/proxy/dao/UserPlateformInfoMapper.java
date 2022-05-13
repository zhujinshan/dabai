package com.dabai.proxy.dao;

import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.po.UserPlateformInfoExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserPlateformInfoMapper extends Mapper<UserPlateformInfo> {
    long countByExample(UserPlateformInfoExample example);

    List<UserPlateformInfo> selectByExample(UserPlateformInfoExample example);

    int updateByExampleSelective(@Param("record") UserPlateformInfo record, @Param("example") UserPlateformInfoExample example);

    int updateByExample(@Param("record") UserPlateformInfo record, @Param("example") UserPlateformInfoExample example);
}