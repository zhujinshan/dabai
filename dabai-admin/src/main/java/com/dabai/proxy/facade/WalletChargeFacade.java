package com.dabai.proxy.facade;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.dabai.proxy.enums.MannualChargeTypeEnum;
import com.dabai.proxy.httpclient.tencentcloud.TencentCosClient;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.req.BatchChargeReq;
import com.dabai.proxy.req.ChargeExcelModel;
import com.dabai.proxy.req.ChargeReq;
import com.dabai.proxy.service.UserInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 20:21
 */
@Service
@Slf4j
public class WalletChargeFacade {
    @Autowired
    private WalletInfoService walletInfoService;

    @Autowired
    private UserPlateformInfoService userPlateformInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TencentCosClient tencentCosClient;

    public Boolean charge(ChargeReq chargeReq) {
        Assert.notNull(chargeReq, "请求入参不能为空");
        if (Objects.isNull(chargeReq.getUserId())) {
            Assert.isTrue(StringUtils.isNotEmpty(chargeReq.getCode()), "会员编码不能为空");
        }
        Assert.notNull(chargeReq.getAmount(), "充值金额不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(chargeReq.getChargeType()), "充值类型不能为空");
        MannualChargeTypeEnum chargeType = MannualChargeTypeEnum.getByName(chargeReq.getChargeType());
        Assert.notNull(chargeType, "未知充值类型");

        Long userId = chargeReq.getUserId();
        if (userId == null) {
            UserPlateformInfo userPlateformInfo = userPlateformInfoService.getByHbxMemberNo(chargeReq.getCode());
            Assert.notNull(chargeType, "未找到会员信息");
            userId = userPlateformInfo.getUserId();
        }
        walletInfoService.mannualCharge(userId, chargeReq.getAmount(), chargeType);
        return Boolean.TRUE;
    }

    public List<ChargeExcelModel> batchCharge(BatchChargeReq batchChargeReq) {
        Assert.notNull(batchChargeReq, "请求入参不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(batchChargeReq.getFile()), "文件不能为空");

        COSObject object = tencentCosClient.getObject(batchChargeReq.getFile());
        COSObjectInputStream objectContent = object.getObjectContent();

        List<ChargeExcelModel> chargeExcelModels = Lists.newArrayList();
        EasyExcel.read(objectContent, ChargeExcelModel.class, new ReadListener<ChargeExcelModel>() {
            @Override
            public void invoke(ChargeExcelModel o, AnalysisContext analysisContext) {
                chargeExcelModels.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet().doRead();
        Assert.notNull(batchChargeReq, "请求入参不能为空");
        Assert.isTrue(chargeExcelModels.size() <= 200, "单次最多充值200个会员");
        List<String> mobiles = chargeExcelModels.stream().filter(e -> StringUtils.isEmpty(e.getCode()) && StringUtils.isNotEmpty(e.getPhone()))
                .map(ChargeExcelModel::getPhone).collect(Collectors.toList());
        List<UserInfo> usersPyMobiles = userInfoService.getUsersPyMobiles(mobiles);
        Map<String, Long> mobileUserIdMap = usersPyMobiles.stream().collect(Collectors.toMap(UserInfo::getMobile, UserInfo::getId));
        for (ChargeExcelModel chargeExcelModel : chargeExcelModels) {
            try {
                ChargeReq chargeReq = new ChargeReq();
                chargeReq.setChargeType(chargeExcelModel.getChargeType());
                chargeReq.setAmount(chargeExcelModel.getAmount());
                if (StringUtils.isNotEmpty(chargeExcelModel.getCode())) {
                    chargeReq.setCode(chargeExcelModel.getCode());
                } else {
                    String phone = chargeExcelModel.getPhone();
                    if (StringUtils.isNotEmpty(phone) && mobileUserIdMap.containsKey(phone)) {
                        chargeReq.setUserId(mobileUserIdMap.get(phone));
                    }
                }
                charge(chargeReq);
                chargeExcelModel.setResult("充值成功");
            } catch (Exception e) {
                log.error("充值异常，chargeExcelModel：{}", chargeExcelModel, e);
                chargeExcelModel.setResult("充值失败：" + e.getMessage());
            }
        }
        return chargeExcelModels;
    }

    public List<ChargeExcelModel> batchCharge(List<ChargeExcelModel> chargeExcelModels) {
        Assert.notNull(chargeExcelModels, "请求入参不能为空");
        Assert.isTrue(chargeExcelModels.size() <= 200, "单次最多充值200个会员");
        List<String> mobiles = chargeExcelModels.stream().filter(e -> StringUtils.isEmpty(e.getCode()) && StringUtils.isNotEmpty(e.getPhone()))
                .map(ChargeExcelModel::getPhone).collect(Collectors.toList());
        List<UserInfo> usersPyMobiles = userInfoService.getUsersPyMobiles(mobiles);
        Map<String, Long> mobileUserIdMap = usersPyMobiles.stream().collect(Collectors.toMap(UserInfo::getMobile, UserInfo::getId));
        for (ChargeExcelModel chargeExcelModel : chargeExcelModels) {
            try {
                ChargeReq chargeReq = new ChargeReq();
                chargeReq.setChargeType(chargeExcelModel.getChargeType());
                chargeReq.setAmount(chargeExcelModel.getAmount());
                if (StringUtils.isNotEmpty(chargeExcelModel.getCode())) {
                    chargeReq.setCode(chargeExcelModel.getCode());
                } else {
                    String phone = chargeExcelModel.getPhone();
                    if (StringUtils.isNotEmpty(phone) && mobileUserIdMap.containsKey(phone)) {
                        chargeReq.setUserId(mobileUserIdMap.get(phone));
                    }
                }
                charge(chargeReq);
                chargeExcelModel.setResult("充值成功");
            } catch (Exception e) {
                log.error("充值异常，chargeExcelModel：{}", chargeExcelModel, e);
                chargeExcelModel.setResult("充值失败：" + e.getMessage());
            }
        }
        return chargeExcelModels;
    }
}
