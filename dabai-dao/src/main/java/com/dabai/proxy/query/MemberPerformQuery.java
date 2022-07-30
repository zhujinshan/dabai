package com.dabai.proxy.query;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:38
 */
@Data
public class MemberPerformQuery {

    private String organizationCode;

    private String name;

    private String mobile;

    private String code;

    private String parentCode;

    private String parentName;

    private Integer changeAgent;

    private Integer originalIdentityTag;

}
