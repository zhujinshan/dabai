package com.dabai.proxy.httpclient.core.handler;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.charset.Charset;

public class JsoupDocumentResultHandler implements ResultHandler<Document> {

	@Override
	public Document handle(HttpEntity entity, Charset defaultCharset) throws Exception {
		String html = EntityUtils.toString(entity, defaultCharset);
		if (html == null) {
			return null;
		}
		return Jsoup.parse(html);
	}

}
