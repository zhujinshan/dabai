package com.dabai.proxy.httpclient.core.metadata;

import com.dabai.proxy.httpclient.api.HttpParam;
import com.dabai.proxy.httpclient.api.metadata.HttpParamMetadata;
import org.springframework.util.StringUtils;

public class HttpParamMetadataImpl implements HttpParamMetadata {

    private final String name;

    private final int index;

    private final boolean required;

    private final boolean encode;

    public HttpParamMetadataImpl(int index) {
        this(index, null, null);
    }

    public HttpParamMetadataImpl(int index, HttpParam param, String defaultName) {
        if (param != null) {
            String paramValue = param.value();
            if (paramValue.isEmpty()) {
                paramValue = param.name();
            }
            if (paramValue.isEmpty()) {
                paramValue = String.valueOf(index);
            }
            this.name = paramValue;
            this.required = param.required();
            this.encode = param.encode();
        } else {
            this.name = StringUtils.isEmpty(defaultName) ? String.valueOf(index) : defaultName;
            this.required = false;
            this.encode = true;
        }
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isEncode() {
        return encode;
    }

}
