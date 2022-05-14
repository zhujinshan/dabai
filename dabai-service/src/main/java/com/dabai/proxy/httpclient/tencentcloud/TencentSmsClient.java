package com.dabai.proxy.httpclient.tencentcloud;

import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.TencentCloudConfig;
import com.dabai.proxy.utils.JsonUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 21:49
 */
@Component
@Slf4j
public class TencentSmsClient {

    @Autowired
    private TencentCloudConfig cloudConfig;

    @Autowired
    private SmsClient smsClient;

    public void sendSubmitVerificationCode(String mobile) {
        String code = RandomStringUtils.randomNumeric(6);
        LocalCache.putCode(mobile, code);
        String[] templateParamSet = {code};
        this.sendMsg(cloudConfig.getSubmitCodeTemplateId(), mobile, templateParamSet);
    }

    public void sendMsg(String templateId, String mobile, String[] param) {
        log.info("发送短信通知：templateId:{},mobile:{},param:{}", templateId, mobile, JsonUtils.toJson(param));
        try {
            SendSmsRequest req = new SendSmsRequest();
            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台: https://console.cloud.tencent.com/smsv2
             * sms helper: https://cloud.tencent.com/document/product/382/3773 */
            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            req.setSmsSdkAppId(cloudConfig.getSdkAppId());
            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
            req.setSignName(cloudConfig.getSignName());
            /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
            req.setTemplateId(templateId);
            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
            String[] phoneNumberSet = {"+86" + mobile};
            req.setPhoneNumberSet(phoneNumberSet);
            /* 模板参数: 若无模板参数，则设置为空 */
            req.setTemplateParamSet(param);
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = smsClient.SendSms(req);
            log.info("发送短信：mobile：{},resp:{}", mobile, JsonUtils.toJson(res));
        } catch (TencentCloudSDKException e) {
            log.error("发送短信异常：mobile: {}", mobile, e);
        }
    }
}
