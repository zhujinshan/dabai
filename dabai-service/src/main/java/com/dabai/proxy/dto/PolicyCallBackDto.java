package com.dabai.proxy.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:16
 */
@Data
public class PolicyCallBackDto {

    private String uid;

    private String timeStamp;

    private List<PolicyInfoDto> data;

}
