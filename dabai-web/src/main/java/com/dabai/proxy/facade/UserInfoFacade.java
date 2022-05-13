package com.dabai.proxy.facade;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:15
 */
public interface UserInfoFacade {

    /**
     * 保存用户手机号聚合接口
     * 同步华宝兴
     *
     * @param openId
     * @param phone
     */
    void saveUserPhone(String openId, String phone);
}
