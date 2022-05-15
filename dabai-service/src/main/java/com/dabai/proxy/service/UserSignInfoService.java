package com.dabai.proxy.service;

import com.dabai.proxy.po.UserSignInfo;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 17:18
 */
public interface UserSignInfoService {

    UserSignInfo getByUserId(Long userId);

    /**
     * 签约中
     * @param userId
     * @param signDealNo
     * @return
     */
    UserSignInfo signing(Long userId, String signDealNo);

    UserSignInfo getByDealNo(String dealNo);

    void updateSignInfo(UserSignInfo userSignInfo);
}
