package com.dabai.proxy.service;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.po.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:15
 */
public interface ProductInfoService {
    /**
     * 获取所有产品列表
     * @return
     */
    List<ProductInfo> pageQuery();
}
