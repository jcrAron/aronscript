package net.jcraron.aronscript.parser.serialcode;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import net.jcraron.aronscript.core.Operator;

public class SerialCodesBuilder {
	public int VariableCounter;
	public byte[] binaryCode;
	public StringBuilder stringCode;

	public SerialCodesBuilder() {
		VariableCounter = 2;
	}

	public SerialCodes build() {
		return null;
	}

	public void newTable() {
	}

	public int defineVariable(String name) {
		return VariableCounter++;
	}

	public void magic(Operator op, int self, int... args) {
		if (op.getParams().length != args.length + 1) {
			throw new IllegalSerialCodeException("magic code length is incorrect");
		}
		// TODO
	}

	/** not parse "null" */
	private byte[] parseValue(String name) {
		return null;
	}

	private static String parseString(String name) {
		String escapedText = "This is tab \\t and \\rthis is on a new line";
		Properties prop = new Properties();
		try {
			prop.load(new StringReader("x=" + escapedText + "\n"));
		} catch (IOException e) {
			// pass
		} catch (IllegalArgumentException e) {
			throw new IllegalSerialCodeException("malformed Unicode escape in string");
		}
		return prop.getProperty("x");
	}
}