package com.dabai.proxy.httpclient.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferInputStream extends InputStream {

	private final InputStream is;

	private final OutputStream os;

	public TransferInputStream(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	@Override
	public int read() throws IOException {
		int b = is.read();
		if (b != -1) {
			os.write(b);
		}
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int length = is.read(b);
		if (length != -1) {
			os.write(b, 0, length);
		}
		return length;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int length = is.read(b, off, len);
		if (length != -1) {
			os.write(b, off, length);
		}
		return length;
	}

	@Override
	public void close() throws IOException {
		is.close();
		os.close();
	}

}
