package net.jcraron.aronscript.parser.script;

import java.io.IOException;
import java.io.InputStreamReader;

import net.jcraron.aronscript.parser.serialcode.Builder;

public class CommonStatement {
	private InputStreamReader reader;
	private int index;
	private Builder builder;

	private void forStatment() {

	}

	private void ifStatment() {

	}

	private void whileStatment() {

	}

	private void functionStatment() {

	}

	private void tableBracket() {
		skipSpace();
		builder.newTable();
		builder.
	}

	private void skipSpace() {
	}

	private boolean startWith(String string) {
		
	}

	private void statmentBracket() {
		skipSpace();
		if (startWith("for")) {

		} else if (startWith("function")) {

		} else if (startWith("while")) {

		} else if (startWith("if")) {

		}
	}
}
