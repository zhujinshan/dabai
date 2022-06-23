package com.dabai.proxy.facade;

import com.dabai.proxy.req.UserPlateformReq;
import com.dabai.proxy.resp.MemberInfoExport;
import com.dabai.proxy.resp.UserInfoResp;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:15
 */
public interface UserInfoFacade {

    /**
     * 保存用户手机号聚合接口
     * 同步华宝兴
     *  @param openId
     * @param phone
     * @param parentId
     */
    void saveUserPhone(String openId, String phone, Long parentId);

    /**
     * 获取会员信息
     * @param openId openId
     * @return 全部信息
     */
    UserInfoResp getUserInfo(String openId);

    /**
     * 更新华保星会员身份信息
     * @param userPlateformReq
     */
    void updateUserPlateformInfo(UserPlateformReq userPlateformReq);

    /**
     * 导出全部数据
     * @return
     */
    List<MemberInfoExport> getAllUserInfo();
}
