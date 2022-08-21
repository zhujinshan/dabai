package com.dabai.proxy.task;

import com.dabai.proxy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/21 10:41
 */
public class UserActionTaskTest extends BaseTest {

    @Autowired
    private UserActionTask userActionTask;

    @Test
    public void action() {
        userActionTask.userActionTask();
    }
}
