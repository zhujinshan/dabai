package com.dabai.proxy.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 16:55
 */
public interface SysStatisticsMapper {

    long realName(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    long policyMemeberCount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal policyAmount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    long cashMemberAmount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal cashAmount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    long agentChangeAmount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int registerCount(@Param("organizationCodes") List<String> organizationCodes, @Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
