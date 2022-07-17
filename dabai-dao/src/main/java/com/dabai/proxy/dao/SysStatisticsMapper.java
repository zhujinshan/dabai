package com.dabai.proxy.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 16:55
 */
public interface SysStatisticsMapper {

    long policyMemeberCount(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal policyAmount(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    long cashMemberAmount(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal cashAmount(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    long agentChangeAmount(@Param("organizationCode") String organizationCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
