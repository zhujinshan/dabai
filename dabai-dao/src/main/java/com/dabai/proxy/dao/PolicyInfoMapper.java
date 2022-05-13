package com.dabai.proxy.dao;

import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.PolicyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface PolicyInfoMapper extends Mapper<PolicyInfo> {
    long countByExample(PolicyInfoExample example);

    List<PolicyInfo> selectByExample(PolicyInfoExample example);

    int updateByExampleSelective(@Param("record") PolicyInfo record, @Param("example") PolicyInfoExample example);

    int updateByExample(@Param("record") PolicyInfo record, @Param("example") PolicyInfoExample example);
}