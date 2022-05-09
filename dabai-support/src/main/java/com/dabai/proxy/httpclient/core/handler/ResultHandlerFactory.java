package com.dabai.proxy.httpclient.core.handler;

import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ResultHandlerFactory {

	private static ResultHandlerFactory INSTANCE = null;

	private Map<Type, ResultHandler<?>> resultHandlerMapping;

	private ResultHandlerFactory() {

	}

	public static ResultHandlerFactory getInstance() {
		if (INSTANCE == null) {

			INSTANCE = new ResultHandlerFactory();
			INSTANCE.resultHandlerMapping = new HashMap<>(50);

			// CharSequence
			INSTANCE.addHandler(new CharSequenceResultHandler());
			INSTANCE.addHandler(new StringResultHandler());
			INSTANCE.addHandler(new StringBuilderResultHandler());
			INSTANCE.addHandler(new StringBufferResultHandler());

			// File
			INSTANCE.addHandler(new FileResultHandler());

			// Stream
			INSTANCE.addHandler(new InputStreamResultHandler());
			INSTANCE.addHandler(new BufferedInputStreamResultHandler());
			try {
				Class.forName("org.apache.commons.codec.binary.Base64InputStream");
				INSTANCE.addHandler(new Base64InputStreamResultHandler());
			} catch (ClassNotFoundException e) {
				// ignore
			}

			// Reader
			INSTANCE.addHandler(new ReaderResultHandler());
			INSTANCE.addHandler(new InputStreamReaderResultHandler());
			INSTANCE.addHandler(new BufferedReaderResultHandler());
			INSTANCE.addHandler(new LineNumberReaderResultHandler());
			INSTANCE.addHandler(new CharArrayReaderResultHandler());

			// Array
			INSTANCE.addHandler(new ByteArrayResultHandler());
			INSTANCE.addHandler(new CharArrayResultHandler());

			// Jsoup
			try {
				Class.forName("org.jsoup.nodes.Document");
				INSTANCE.addHandler(new JsoupDocumentResultHandler());
			} catch (ClassNotFoundException e) {
				// ignore
			}
			
			//XML
			INSTANCE.addHandler(new W3cDocumentResultHandler());
		}
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public <T> ResultHandler<T> getHandler(Class<T> type) {
		return (ResultHandler<T>) resultHandlerMapping.get(type);
	}

	public void addHandler(ResultHandler<?> handler) {
		Type type = getHandlerType(handler);
		resultHandlerMapping.put(type, handler);
	}

	public void removeHandler(Type type) {
		resultHandlerMapping.remove(type);
	}

	private Type getHandlerType(ResultHandler<?> handler) {
		Type type = ResolvableType.forInstance(handler).as(ResultHandler.class).getGeneric(0).getType();
		return type;
	}
}
