package com.dabai.proxy.dao;

import com.dabai.proxy.po.WalletFlow;
import com.dabai.proxy.po.WalletFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface WalletFlowMapper extends Mapper<WalletFlow> {
    long countByExample(WalletFlowExample example);

    List<WalletFlow> selectByExample(WalletFlowExample example);

    int updateByExampleSelective(@Param("record") WalletFlow record, @Param("example") WalletFlowExample example);

    int updateByExample(@Param("record") WalletFlow record, @Param("example") WalletFlowExample example);
}