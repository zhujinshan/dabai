package com.dabai.proxy.encypt.config;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 13:48
 */
public class DecryptConfig {

    private String appid;

    private String appsecret;

    private Boolean checkTime = true;

    private Integer effectivePeriod = 5;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public Boolean getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Boolean checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getEffectivePeriod() {
        return effectivePeriod;
    }

    public void setEffectivePeriod(Integer effectivePeriod) {
        this.effectivePeriod = effectivePeriod;
    }

}
