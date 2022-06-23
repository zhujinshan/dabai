package com.dabai.proxy.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/6/23 22:08
 */
@Data
@HeadFontStyle(fontHeightInPoints = 12)
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(28)
public class MemberInfoExport {

    @ExcelProperty(value = "会员编码")
    private String memberNo;

    @ExcelProperty(value = "会员手机号")
    private String phone;

    @ExcelProperty(value = "身份证")
    private String idCard;

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "所属机构编码")
    private String organizationCode;

    @ExcelProperty(value = "注册时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    @ExcelProperty(value = "邀请人会员编码")
    private String parentMemberNo;

    @ExcelProperty(value = "邀请人手机号")
    private String parentPhone;

    @ExcelProperty(value = "是否代理人")
    private String identityTag;

    @ExcelProperty(value = "总保费")
    private BigDecimal totalFee;

    @ExcelProperty(value = "总推广金额")
    private BigDecimal totalAmount;

    @ExcelProperty(value = "总提现金额")
    private BigDecimal totalCashedAmount;

}
