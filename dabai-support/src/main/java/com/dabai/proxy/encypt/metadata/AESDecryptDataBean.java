package com.dabai.proxy.encypt.metadata;


import java.lang.reflect.Method;

/**
 * aes解密源数据
 *
 * @author: jinshan.zhu
 * @date: 2020/9/21 13:48
 */
public class AESDecryptDataBean {

    /**
     * 目标属性读方法
     */
    private Method targetReadMethod;

    /**
     * 目标属性写方法
     */
    private Method targetWriteMethod;


    public Method getTargetReadMethod() {
        return targetReadMethod;
    }

    public void setTargetReadMethod(Method targetReadMethod) {
        this.targetReadMethod = targetReadMethod;
    }

    public Method getTargetWriteMethod() {
        return targetWriteMethod;
    }

    public void setTargetWriteMethod(Method targetWriteMethod) {
        this.targetWriteMethod = targetWriteMethod;
    }

}
