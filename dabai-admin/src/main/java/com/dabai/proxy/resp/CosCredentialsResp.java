package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 23:10
 */
@Data
@ApiModel(value = "临时凭证")
public class CosCredentialsResp {
    private String tmpSecretId;
    private String tmpSecretKey;
    private String sessionToken;
    private String token;
}
