package com.dabai.proxy.task;

import com.dabai.proxy.BaseTest;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.controller.OperationalDataController;
import com.dabai.proxy.req.OperationalDataReq;
import com.dabai.proxy.resp.OperationalDataResp;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/21 10:41
 */
@Slf4j
public class UserActionTaskTest extends BaseTest {

    @Autowired
    private UserActionTask userActionTask;
    @Autowired
    private OperationalDataController operationalDataController;

    @Test
    public void action() {
        userActionTask.userActionTask();
    }

    @Test
    public void testQuery() {
        OperationalDataReq req = new OperationalDataReq();
        req.setOrganizationCode(Lists.newArrayList("00, 11"));

        Pair<Date, Date> tDate = getTDate(30);
        req.setStartTime(tDate.getLeft());
        req.setEndTime(tDate.getRight());
        Result<List<OperationalDataResp>> query = operationalDataController.query(req);
        log.info("result:{}", query);
    }

    private Pair<Date, Date> getTDate(Integer days) {
        LocalDateTime yesterDayBegin = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
        LocalDateTime yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
        Date startDate = Date.from(yesterDayBegin.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(yesterDayEnd.atZone(ZoneId.systemDefault()).toInstant());
        return Pair.of(startDate, endDate);
    }
}
