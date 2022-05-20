package com.dabai.proxy.facade;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.po.ProductInfo;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:37
 */
public interface ProductInfoFacade {
    /**
     * 获取所有品类产品列表
     * @return
     */
    List<ProductInfo> pageQuery();
}
