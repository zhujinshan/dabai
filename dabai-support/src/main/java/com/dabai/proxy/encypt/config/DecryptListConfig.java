package com.dabai.proxy.encypt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/22 16:09
 */
@ConfigurationProperties(prefix = "api-encrypt")
public class DecryptListConfig {

    private Boolean isDebug = false;

    private Integer order;

    private Boolean enableAes = false;

    private List<DecryptConfig> list;

    public Boolean getDebug() {
        return isDebug;
    }

    public void setDebug(Boolean debug) {
        isDebug = debug;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getEnableAes() {
        return enableAes;
    }

    public void setEnableAes(Boolean enableAes) {
        this.enableAes = enableAes;
    }

    public List<DecryptConfig> getList() {
        return list;
    }

    public void setList(List<DecryptConfig> list) {
        this.list = list;
    }
}
