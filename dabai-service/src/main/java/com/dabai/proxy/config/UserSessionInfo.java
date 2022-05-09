package com.dabai.proxy.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 23:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionInfo {

    private String openId;

    private String sessionKey;
}
