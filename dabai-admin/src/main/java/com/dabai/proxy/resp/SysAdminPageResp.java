package com.dabai.proxy.resp;

import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/31 13:48
 */
@Data
public class SysAdminPageResp {

    private Long total;

    private List<SysAdminDTO> list;
}
