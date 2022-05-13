package com.dabai.proxy.dao;

import com.dabai.proxy.po.WalletInfo;
import com.dabai.proxy.po.WalletInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface WalletInfoMapper extends Mapper<WalletInfo> {
    long countByExample(WalletInfoExample example);

    List<WalletInfo> selectByExample(WalletInfoExample example);

    int updateByExampleSelective(@Param("record") WalletInfo record, @Param("example") WalletInfoExample example);

    int updateByExample(@Param("record") WalletInfo record, @Param("example") WalletInfoExample example);
}