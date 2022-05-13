package com.dabai.proxy.dao;

import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.CashSnapshotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CashSnapshotMapper extends Mapper<CashSnapshot> {
    long countByExample(CashSnapshotExample example);

    List<CashSnapshot> selectByExample(CashSnapshotExample example);

    int updateByExampleSelective(@Param("record") CashSnapshot record, @Param("example") CashSnapshotExample example);

    int updateByExample(@Param("record") CashSnapshot record, @Param("example") CashSnapshotExample example);
}