package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.nio.charset.Charset;

public class W3cDocumentResultHandler implements ResultHandler<Document> {

	private DocumentBuilderFactory documentBuilderFactory;

	public W3cDocumentResultHandler() {
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public Document handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		try (InputStream input = entity.getContent()) {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(input);
			return doc;
		}
	}

}
