package com.dabai.proxy.service;

import com.dabai.proxy.httpclient.liness.param.TransferToBankCardParam;
import com.dabai.proxy.po.CashSnapshot;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 23:00
 */
public interface CashSnapshotService {

    /**
     * 初始化提现信息
     *
     * @param userId                  用户id
     * @param mobile                  手机号
     * @param transferToBankCardParam 入参
     * @return 提现信息
     */
    CashSnapshot init(Long userId, String mobile, TransferToBankCardParam transferToBankCardParam);


    /**
     * 提现提交失败
     *
     * @param id        快照id
     * @param msg       信息
     * @param thirdResp 三方报文
     */
    void cashFailed(Long id, String msg, String thirdResp);

    /**
     * 通过请求查找提现记录
     *
     * @param requestNo requestNo
     * @return res
     */
    CashSnapshot getByRequestNo(String requestNo);

    /**
     * 查询提现列表
     *
     * @param userId 用户id
     * @return resut
     */
    List<CashSnapshot> pageQuery(Long userId, Long cashInfoId);
}
