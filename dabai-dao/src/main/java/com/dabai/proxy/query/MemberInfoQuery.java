package com.dabai.proxy.query;

import lombok.Data;

import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:38
 */
@Data
public class MemberInfoQuery {

    private String organizationCode;

    private String name;

    private String mobile;

    private String code;

    private Integer status;

    private String parentCode;

    private String parentName;

    private String parentMobile;

    private Date registerStartTime;

    private Date redisterEndtime;

    private String idCard;

    private Integer changeAgent;

    private Date changeAgentStartTime;

    private Date changeAgentEndtime;

    private Integer originalIdentityTag;

    private Date realNameStartTime;

    private Date realNameEndTime;
}
