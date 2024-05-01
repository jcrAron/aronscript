package net.jcraron.aronscript.parser.scanner;

public enum StringType {
	NUMBER, NAME, STRING, BOOLEAN, BRACKETS_START, BRACKETS_END, COMMA, OPERATOR;

	public StringType() {
		
	}

	public StringType getStringType(char ch) {
		return null;
	}
}
