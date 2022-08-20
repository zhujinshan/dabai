package com.dabai.proxy.event;

import com.dabai.proxy.dao.UserActionMapper;
import com.dabai.proxy.po.UserAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/20 20:51
 */
@Component
@Slf4j
public class UserActionEventListener implements ApplicationListener<UserActionEvent> {

    @Resource
    private UserActionMapper userActionMapper;

    @Override
    @Async
    public void onApplicationEvent(UserActionEvent event) {
        log.info("UserActionEventListener onApplicationEvent event:{}", event);
        UserAction userAction = new UserAction();
        userAction.setAction(event.getAction().getCode());
        userAction.setUserId(event.getUserId());
        userAction.setCtime(new Date());
        userActionMapper.insertSelective(userAction);
    }
}
