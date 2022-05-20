package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:09
 */
@Data
@ApiModel(value = "分页信息")
public class Paging {

    private int offset = 0;

    private int limit = 20;
}
