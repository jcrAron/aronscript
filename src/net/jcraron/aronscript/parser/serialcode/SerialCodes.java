package net.jcraron.aronscript.parser.serialcode;

import java.io.File;

public class SerialCodes {
	private String[][] stringCodes;
	private byte[] binaryCodes;

	SerialCodes(byte[] binaryCodes, String[][] stringCodes) {
		this.stringCodes = stringCodes;
		this.binaryCodes = binaryCodes;
	}

	public String[][] getStringCodes() {
		return stringCodes;
	}

	public byte[] getBinaryCodes() {
		return binaryCodes;
	}

	public String[] getOptimization() {
		// TODO
		return null;
	}

	/** load binary code from file */
	public static SerialCodes loadFromFile(File filename) {
		// TODO
		return null;
	}

	/** only save binary code */
	public void saveToFile(File filename) {
		// TODO
	}
}
