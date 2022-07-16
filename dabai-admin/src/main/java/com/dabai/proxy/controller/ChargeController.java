package com.dabai.proxy.controller;

import com.alibaba.excel.EasyExcel;
import com.dabai.proxy.config.AdminUserSessionContext;
import com.dabai.proxy.config.AdminUserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.facade.WalletChargeFacade;
import com.dabai.proxy.req.BatchChargeReq;
import com.dabai.proxy.req.ChargeExcelModel;
import com.dabai.proxy.req.ChargeReq;
import com.dabai.proxy.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.Assert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 17:42
 */
@RestController
@RequestMapping("/wallet")
@Api(tags = "会员充值")
@Slf4j
public class ChargeController {

    @Autowired
    WalletChargeFacade walletChargeFacade;

    @PostMapping(value = "/charge")
    @ApiOperation(value = "会员充值", httpMethod = "POST")
    @PathRole(role = SysAdminRole.SUPPER_ADMIN)
    public Result<Boolean> charge(@ApiParam(value = "充值入参", required = true) @RequestBody ChargeReq chargeReq) {
        AdminUserSessionInfo adminUserSessionInfo = AdminUserSessionContext.getAdminUserSessionInfo();
        Assert.isTrue(adminUserSessionInfo != null && adminUserSessionInfo.getCharge(), "无权限操作充值功能");
        return Result.success(walletChargeFacade.charge(chargeReq));
    }

    @PostMapping(value = "/batchCharge")
    @ApiOperation(value = "批量会员充值", httpMethod = "POST")
    @PathRole(role = SysAdminRole.SUPPER_ADMIN)
    public void batchCharge(@ApiParam(value = "充值入参", required = true) @RequestBody BatchChargeReq batchChargeReq,
                                       HttpServletResponse response) throws IOException {
        AdminUserSessionInfo adminUserSessionInfo = AdminUserSessionContext.getAdminUserSessionInfo();
        Assert.isTrue(adminUserSessionInfo != null && adminUserSessionInfo.getCharge(), "无权限操作充值功能");
        List<ChargeExcelModel> chargeExcelModels = walletChargeFacade.batchCharge(batchChargeReq);
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + "充值结果-" + System.currentTimeMillis() + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            ServletOutputStream outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, ChargeExcelModel.class).sheet("充值结果").doWrite(chargeExcelModels);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Result<Boolean> result = Result.genResult(-1, "下载失败：" + e.getMessage(), null);
            response.getWriter().println(JsonUtils.toJson(result));
        }
    }
}
