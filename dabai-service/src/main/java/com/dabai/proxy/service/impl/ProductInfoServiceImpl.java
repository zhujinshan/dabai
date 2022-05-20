package com.dabai.proxy.service.impl;

import com.dabai.proxy.dao.PolicyInfoMapper;
import com.dabai.proxy.dao.ProductInfoMapper;
import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.PolicyInfo;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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
}
