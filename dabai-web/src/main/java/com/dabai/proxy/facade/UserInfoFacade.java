package com.dabai.proxy.facade;

import com.dabai.proxy.resp.UserInfoResp;

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

    /**
     * 获取会员信息
     * @param openId openId
     * @return 全部信息
     */
    UserInfoResp getUserInfo(String openId);
}
