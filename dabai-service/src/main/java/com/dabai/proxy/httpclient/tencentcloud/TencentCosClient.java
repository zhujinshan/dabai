package com.dabai.proxy.httpclient.tencentcloud;

import com.dabai.proxy.config.TencentCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.Assert;

import java.net.URL;
import java.util.Date;
import java.util.TreeMap;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 21:37
 */
@Service
@Slf4j
public class TencentCosClient {

    @Autowired
    private TencentCosConfig tencentCosConfig;

    @Autowired(required = false)
    private COSClient cosClient;

    public URL generatePresignedUrl(String key) {
        Assert.notNull(cosClient, "未初始化cosClient");
        Assert.notNull(tencentCosConfig.getBucket(), "未设置bucket");
        // 半小时过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        URL url = cosClient.generatePresignedUrl(tencentCosConfig.getBucket(),
                key, expirationDate, HttpMethodName.GET, null, null);
        log.info("{}, generatePresignedUrl: {}", key, url);
        return url;
    }

    /**
     * 下载
     * COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
     * // 下载对象的 CRC64
     * String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
     *
     * @param key
     * @return
     */
    public COSObject getObject(String key) {
        Assert.notNull(cosClient, "未初始化cosClient");
        Assert.notNull(tencentCosConfig.getBucket(), "未设置bucket");
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(tencentCosConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

    public Response getCredential() {
        Assert.notNull(tencentCosConfig.getBucket(), "未设置bucket");

        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            // 云 api 密钥 SecretId
            config.put("secretId", tencentCosConfig.getSecretId());
            // 云 api 密钥 SecretKey
            config.put("secretKey", tencentCosConfig.getSecretKey());
            // 设置域名
            //config.put("host", "sts.internal.tencentcloudapi.com");

            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config.put("durationSeconds", 1800);

            // 换成你的 bucket
            config.put("bucket", tencentCosConfig.getBucket());
            // 换成 bucket 所在地区
            config.put("region", "ap-beijing");

            Response response = CosStsClient.getCredential(config);
            System.out.println(response.credentials.tmpSecretId);
            System.out.println(response.credentials.tmpSecretKey);
            System.out.println(response.credentials.sessionToken);
            return response;
        } catch (Exception e) {
            log.error("getCredential error");
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
