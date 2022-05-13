package com.dabai.proxy.service;

import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:28
 */
public interface UserPlateformInfoService {

    void save(Long userId, MemberInfoResp memberInfoResp);
}
