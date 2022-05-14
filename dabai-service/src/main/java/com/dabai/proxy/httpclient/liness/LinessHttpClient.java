package com.dabai.proxy.httpclient.liness;

import com.dabai.proxy.httpclient.api.BeforeRequest;
import com.dabai.proxy.httpclient.api.DataType;
import com.dabai.proxy.httpclient.api.HttpClient;
import com.dabai.proxy.httpclient.api.HttpEntity;
import com.dabai.proxy.httpclient.api.HttpMapping;
import com.dabai.proxy.httpclient.api.RequestMethod;
import com.dabai.proxy.httpclient.liness.param.AddMerchantParam;
import com.dabai.proxy.httpclient.liness.param.FactorsVerifyParam;
import com.dabai.proxy.httpclient.liness.param.QuerySignAgreementParam;
import com.dabai.proxy.httpclient.liness.param.QueryTransferParam;
import com.dabai.proxy.httpclient.liness.param.SignAgreementParam;
import com.dabai.proxy.httpclient.liness.param.TransferToBankCardParam;
import com.dabai.proxy.httpclient.liness.resp.AddMerchantResult;
import com.dabai.proxy.httpclient.liness.resp.FactorsVerifyResult;
import com.dabai.proxy.httpclient.liness.resp.LinessBaseResult;
import com.dabai.proxy.httpclient.liness.resp.QuerySignAgreementResult;
import com.dabai.proxy.httpclient.liness.resp.QueryTransferResult;
import com.dabai.proxy.httpclient.liness.resp.SignAgreementResult;
import com.dabai.proxy.httpclient.liness.resp.TransferToBankCardResult;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/8 12:24
 */
@HttpClient
@HttpMapping("${http.liness.host}")
@BeforeRequest(LinessHttpClientProcessor.class)
public interface LinessHttpClient {

    String SUCCESS_CODE = "0000";

    /**
     * 增加下发收款人
     *
     * @param addMerchantParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/contract/addmerchanttransferreceiver.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<AddMerchantResult> addMerchant(@HttpEntity AddMerchantParam addMerchantParam);


    /**
     * 三要素校验
     *
     * @param factorsVerifyParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/verify/3factorsverify.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<FactorsVerifyResult> factorsVerify(@HttpEntity FactorsVerifyParam factorsVerifyParam);


    /**
     * 用户签约协议
     * 上传结果以ret_code=“0000”表示签约成功，其他返回码表示失败
     * serial_no  与 签约提交的 流水号  相同
     * deal_no   签约系统的 流水号
     * 收到回调 ，请返回大写的  SUCCESS
     * 如果没有收到或者通知网络异常。会在10秒后重复通知。最多通知 10次
     *
     * @param signAgreementParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/contract/signagreement.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<SignAgreementResult> signAgreement(@HttpEntity SignAgreementParam signAgreementParam);

    /**
     * 下发银行卡
     *
     * @param transferToBankCardParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/transfer/transfertobankcard.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<TransferToBankCardResult> transferToBankcard(@HttpEntity TransferToBankCardParam transferToBankCardParam);


    /**
     * 商户用户签约查询
     *
     * @param querySignAgreementParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/query/querymerchantusercontract.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<QuerySignAgreementResult> queryMerchantUserContract(@HttpEntity QuerySignAgreementParam querySignAgreementParam);


    /**
     * 商户单笔下发单状态查询
     *
     * @param queryTransferParam 入参
     * @return result
     */
    @HttpMapping(path = "/api/query/querytransferdeal.do", method = RequestMethod.POST,
            dataType = DataType.JSON)
    LinessBaseResult<QueryTransferResult> queryTransferDeal(@HttpEntity QueryTransferParam queryTransferParam);



}
