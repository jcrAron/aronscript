package net.jcraron.aronscript.parser.scanner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Scanner implements Iterable<Part>, Iterator<Part> {

	InputStreamReader reader;

	public Scanner(String string) throws UnsupportedEncodingException {
		this(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)));
	}

	public Scanner(InputStream in) {
		this.reader = new InputStreamReader(in, StandardCharsets.UTF_8);
		if (this.reader.markSupported()) {
			throw new RuntimeException("stream is not support mark");
		}
	}

	private boolean hasNextChar() throws IOException {
		return this.reader.ready();
	}

	private char pollChar() throws IOException {
		return (char) this.reader.read();
	}

	/* return 0 if reader not next char */
	private char peekChar() throws IOException {
		if (this.reader.ready()) {
			return 0;
		}
		this.reader.mark(1);
		char peek = (char) this.reader.read();
		this.reader.reset();
		return peek;
	}

	@Override
	public Part next() {
		StringBuilder sb = new StringBuilder();
		StringType flag = null;
		while (hasNextChar()) {
			char ch = peekChar();
			sb.append(ch);
			if (!isSpace(ch)) {

			}
			if (shouldSplit(ch)) {
				break;
			}
		}
		return new Part(flag, sb.toString());
	}

	@Override
	public boolean hasNext() {
		// TODO
		return false;
	}

	@Override
	public Iterator<Part> iterator() {
		return this;
	}
}
