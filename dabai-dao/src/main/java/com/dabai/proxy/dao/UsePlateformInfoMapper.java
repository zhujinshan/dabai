package com.dabai.proxy.dao;

import com.dabai.proxy.po.UsePlateformInfo;
import com.dabai.proxy.po.UsePlateformInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UsePlateformInfoMapper extends Mapper<UsePlateformInfo> {
    long countByExample(UsePlateformInfoExample example);

    List<UsePlateformInfo> selectByExample(UsePlateformInfoExample example);

    int updateByExampleSelective(@Param("record") UsePlateformInfo record, @Param("example") UsePlateformInfoExample example);

    int updateByExample(@Param("record") UsePlateformInfo record, @Param("example") UsePlateformInfoExample example);
}