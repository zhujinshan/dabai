package com.dabai.proxy.query;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/31 13:40
 */
@Data
public class SysAdminQuery {

    private Long createUserId;

    private Integer role;

    private String mobile;

    private Integer status;
}
