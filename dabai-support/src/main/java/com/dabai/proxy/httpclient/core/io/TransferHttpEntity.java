package com.dabai.proxy.httpclient.core.io;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferHttpEntity implements HttpEntity{
	
	private final HttpEntity httpEntity;
	
	private final OutputStream os;
	
	public TransferHttpEntity(HttpEntity httpEntity, OutputStream os){
		this.httpEntity = httpEntity;
		this.os = os;
	}

	@Override
	public boolean isRepeatable() {
		//不可重复
		return false;
	}

	@Override
	public boolean isChunked() {
		return httpEntity.isChunked();
	}

	@Override
	public long getContentLength() {
		return httpEntity.getContentLength();
	}

	@Override
	public Header getContentType() {
		return httpEntity.getContentType();
	}

	@Override
	public Header getContentEncoding() {
		return httpEntity.getContentEncoding();
	}

	@Override
	public InputStream getContent() throws IOException, UnsupportedOperationException {
		InputStream is = httpEntity.getContent();
		if(is == null){
			return null;
		}
		return new TransferInputStream(is, os);
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		this.httpEntity.writeTo(outstream);
	}

	@Override
	public boolean isStreaming() {
		//采用了流
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void consumeContent() throws IOException {
		this.httpEntity.consumeContent();
	}

}
