package com.dabai.proxy.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 产品编码
     */
    private String code;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 标签：热销爆款
     */
    private String label;

    /**
     * 图片地址
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 产品跳转链接
     */
    @Column(name = "h5_url")
    private String h5Url;

    /**
     * 最小起订金额(元)
     */
    @Column(name = "min_amount")
    private Long minAmount;

    /**
     * 产品简介
     */
    @Column(name = "brief_introduction")
    private String briefIntroduction;

    /**
     * 保险品类：车险/财产险
     */
    private String category;

    /**
     * 推广费比例
     */
    @Column(name = "commission_radio")
    private Long commissionRadio;

    private Date ctime;

    private Date utime;

    /**
     * 0:废弃；1：可用
     */
    @Column(name = "valid")
    private Integer valid;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取产品编码
     *
     * @return code - 产品编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置产品编码
     *
     * @param code 产品编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取产品名称
     *
     * @return name - 产品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置产品名称
     *
     * @param name 产品名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取标签：热销爆款
     *
     * @return label - 标签：热销爆款
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置标签：热销爆款
     *
     * @param label 标签：热销爆款
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 获取图片地址
     *
     * @return img_url - 图片地址
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片地址
     *
     * @param imgUrl 图片地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * 获取产品跳转链接
     *
     * @return h5_url - 产品跳转链接
     */
    public String getH5Url() {
        return h5Url;
    }

    /**
     * 设置产品跳转链接
     *
     * @param h5Url 产品跳转链接
     */
    public void setH5Url(String h5Url) {
        this.h5Url = h5Url == null ? null : h5Url.trim();
    }

    /**
     * 获取最小起订金额(元)
     *
     * @return min_amount - 最小起订金额(元)
     */
    public Long getMinAmount() {
        return minAmount;
    }

    /**
     * 设置最小起订金额(元)
     *
     * @param minAmount 最小起订金额(元)
     */
    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    /**
     * 获取产品简介
     *
     * @return brief_introduction - 产品简介
     */
    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    /**
     * 设置产品简介
     *
     * @param briefIntroduction 产品简介
     */
    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction == null ? null : briefIntroduction.trim();
    }

    /**
     * 获取保险品类：车险/财产险
     *
     * @return category - 保险品类：车险/财产险
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置保险品类：车险/财产险
     *
     * @param category 保险品类：车险/财产险
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * 获取推广费比例
     *
     * @return commission_radio - 推广费比例
     */
    public Long getCommissionRadio() {
        return commissionRadio;
    }

    /**
     * 设置推广费比例
     *
     * @param commissionRadio 推广费比例
     */
    public void setCommissionRadio(Long commissionRadio) {
        this.commissionRadio = commissionRadio;
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return utime
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * @param utime
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }

    /**
     * 获取0:废弃；1：可用
     *
     * @return valid - 0:废弃；1：可用
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * 设置0:废弃；1：可用
     *
     * @param valid 0:废弃；1：可用
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}