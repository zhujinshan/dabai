package com.dabai.proxy.service;

import com.dabai.proxy.po.ProductInfo;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:15
 */
public interface ProductInfoService {
    /**
     * 获取所有产品列表
     *
     * @return
     */
    List<ProductInfo> pageQuery();

    /**
     * 根据产品编码获取产品
     * @param productCode
     * @return
     */
    ProductInfo getByProductCode(String productCode);
}
