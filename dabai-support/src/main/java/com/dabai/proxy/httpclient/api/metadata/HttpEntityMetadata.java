package com.dabai.proxy.httpclient.api.metadata;


import com.dabai.proxy.httpclient.api.DataType;

public interface HttpEntityMetadata{
	
	int index();
	
	DataType getDataType();
	
	boolean containsJsonView();
	
	Class<?> getJsonViewClass();

}
