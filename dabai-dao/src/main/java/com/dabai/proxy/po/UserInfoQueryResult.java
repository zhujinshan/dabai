package com.dabai.proxy.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 17:34
 */
@Data
public class UserInfoQueryResult {

    private Long id;

    private Long parentId;

    private String name;

    private String idCard;

    private String mobile;

    private String bankCard;

    private String code;

    private String organizationCode;

    private Integer signStatus;

    private String parentCode;

    private String parentName;

    private String parentMobile;

    private Date ctime;

    private Date signUtime;

    private Integer originalIdentityTag;

    private Integer currentIdentityTag;

    private Date identityChangeTime;

    private Integer registerIdentityTag;
}
