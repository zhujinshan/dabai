package com.dabai.proxy.facade.impl;

import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.facade.PolicyInfoFacade;
import com.dabai.proxy.facade.ProductInfoFacade;
import com.dabai.proxy.po.ProductInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.service.PolicyInfoService;
import com.dabai.proxy.service.ProductInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:40
 */
@Slf4j
@Service
public class ProductInfoFacadeImpl implements ProductInfoFacade {
    @Resource
    private ProductInfoService productInfoService;

    @Override
    public List<ProductInfo> pageQuery() {
        return productInfoService.pageQuery();
    }
}
