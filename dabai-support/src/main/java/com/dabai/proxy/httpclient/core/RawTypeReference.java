package com.dabai.proxy.httpclient.core;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

public class RawTypeReference extends TypeReference<Object>{

	private final Type type;
	
	public RawTypeReference(Type type){
		if(type == null){
			throw new NullPointerException();
		}
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}
	
}
