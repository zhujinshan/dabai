package com.dabai.proxy.req;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import org.apache.poi.ss.usermodel.Font;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 23:30
 */
@Data
@HeadFontStyle(fontHeightInPoints = 12)
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(28)
public class ChargeExcelModel {

    @ExcelProperty("会员编码")
    private String code;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("充值金额")
    private BigDecimal amount;

    @ExcelProperty("充值类型")
    private String chargeType;

    @ExcelProperty("充值结果")
    @ContentFontStyle(color = Font.COLOR_RED)
    private String result;
}
