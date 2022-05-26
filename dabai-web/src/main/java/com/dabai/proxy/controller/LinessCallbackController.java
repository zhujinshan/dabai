package com.dabai.proxy.controller;

import com.dabai.proxy.dto.SignNotifyParam;
import com.dabai.proxy.dto.SucNotifyParam;
import com.dabai.proxy.enums.CashStatusEnum;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.UserSignInfo;
import com.dabai.proxy.service.CashSnapshotService;
import com.dabai.proxy.service.UserSignInfoService;
import com.dabai.proxy.service.WalletInfoService;
import com.dabai.proxy.utils.JsonUtils;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 18:45
 */
@Slf4j
@RestController
@RequestMapping("/liness")
@Api(tags = "融富通回调接口")
public class LinessCallbackController {

    @Value("${http.liness.zxRasKey}")
    private String zxRasKey;

    @Autowired
    private UserSignInfoService userSignInfoService;

    @Autowired
    private CashSnapshotService cashSnapshotService;

    @Autowired
    private WalletInfoService walletInfoService;

    @PostMapping("/sign/zx")
    public String zxSignCallback(@RequestBody SignNotifyParam signNotifyParam) {
        log.info("签约通知，参数：{}", signNotifyParam);
        if (Objects.isNull(signNotifyParam) || StringUtils.isEmpty(signNotifyParam.getDealNo())) {
            return "FAILED";
        }
        try {
            if (!this.checkSign(JsonUtils.toMap(signNotifyParam))) {
                return "FAILED";
            }
        } catch (Exception e) {
            log.error("签名延签失败：{}", JsonUtils.toJson(signNotifyParam), e);
        }

        UserSignInfo userSignInfo = userSignInfoService.getByDealNo(signNotifyParam.getDealNo());
        if (Objects.isNull(userSignInfo)) {
            return "FAILED";
        }
        if ("S".equals(signNotifyParam.getRetCode())) {
            userSignInfo.setSignStatus(UserSignStatusEnum.SUCCESS.getCode());
        } else {
            userSignInfo.setSignStatus(UserSignStatusEnum.FAILED.getCode());
            userSignInfo.setRemark(JsonUtils.toJson(signNotifyParam));
        }

        userSignInfoService.updateSignInfo(userSignInfo);
        return "SUCCESS";
    }

    @PostMapping("/cash/zx")
    public String zxCashCallback(@RequestBody SucNotifyParam sucNotifyParam) {
        log.info("下发通知接口，参数：{}", sucNotifyParam);
        if (Objects.isNull(sucNotifyParam) || StringUtils.isEmpty(sucNotifyParam.getRequestNo())) {
            return "FAILED";
        }
        try {
            if (!this.checkSign(JsonUtils.toMap(sucNotifyParam))) {
                return "FAILED";
            }
        } catch (Exception e) {
            log.error("签名延签失败：{}", sucNotifyParam, e);
        }

        CashSnapshot cashSnapshot = cashSnapshotService.getByRequestNo(sucNotifyParam.getRequestNo());
        if (Objects.isNull(cashSnapshot)) {
            return "FAILED";
        }

        if (cashSnapshot.getStatus().equals(CashStatusEnum.SUCCESS.getCode())) {
            log.warn("重复处理单据 不再处理");
            return "SUCCESS";
        }

        if ("C1".equals(sucNotifyParam.getDealStatus())) {
            walletInfoService.cashSuccess(cashSnapshot.getUserId(), cashSnapshot);
        } else {
            cashSnapshot.setRemark(sucNotifyParam.getDealStatusMsg());
            cashSnapshot.setStatus(CashStatusEnum.FAILED.getCode());
            cashSnapshot.setThirdResponse(JsonUtils.toJson(sucNotifyParam));
            walletInfoService.cashFailed(cashSnapshot.getUserId(), cashSnapshot);
        }
        return "SUCCESS";
    }

    private boolean checkSign(Map<String, Object> param) {
        Object sign = param.remove("sign");
        if (Objects.isNull(sign) || StringUtils.isEmpty(String.valueOf(sign))) {
            return false;
        }
        try {
            String target = this.getMD5(param);
            return sign.equals(target);
        } catch (NoSuchAlgorithmException e) {
            log.error("通知结果验签失败：{}", JsonUtils.toJson(param), e);
            return false;
        }
    }

    private String getMD5(Map<String, Object> param) throws NoSuchAlgorithmException {
        TreeMap<String, Object> sortMap = Maps.newTreeMap();
        sortMap.putAll(param);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if (Objects.nonNull(entry.getValue()) && StringUtils.isNotEmpty(String.valueOf(entry.getValue()))) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sb.append("key=").append(zxRasKey);
        MessageDigest messageDigest = MessageDigest.getInstance("sha-256");
        messageDigest.update(sb.toString().getBytes(StandardCharsets.UTF_8));
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(messageDigest.digest());
    }
}
