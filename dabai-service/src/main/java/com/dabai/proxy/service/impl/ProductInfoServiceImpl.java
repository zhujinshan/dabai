package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.ProductInfoMapper;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:50
 */
@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> pageQuery() {
        Example example = new Example(ProductInfo.class);
        example.createCriteria().andEqualTo("valid", 1);
        example.setOrderByClause("id desc");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        return productInfos;
    }

    @Override
    public ProductInfo getByProductCode(String productCode) {
        Example example = new Example(ProductInfo.class);
        example.createCriteria().andEqualTo("code", productCode);
        ProductInfo productInfo = productInfoMapper.selectOneByExample(example);
        return productInfo;
    }
}
