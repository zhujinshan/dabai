package com.dabai.proxy.event;

import com.dabai.proxy.enums.UserActionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/20 19:16
 */
@Getter
@Setter
@ToString
public class UserActionEvent extends ApplicationEvent {

    private UserActionEnum action;

    private Long userId;

    public UserActionEvent(UserActionEnum action, Long userId) {
        super(action);
        this.action = action;
        this.userId = userId;
    }

}
