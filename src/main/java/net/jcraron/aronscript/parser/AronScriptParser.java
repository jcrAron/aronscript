package net.jcraron.aronscript.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.parser.script.statements.BlockStatement;
import net.jcraron.aronscript.util.SubString;

public class AronScriptParser {

	public static FunctionData parseToFunction(File filepath) throws FileNotFoundException, IOException {
		try {
			StringBuilder sb = new StringBuilder();
			try (FileInputStream fileInput = new FileInputStream(filepath)) {
				try (InputStreamReader reader = new InputStreamReader(fileInput, StandardCharsets.UTF_8)) {
					while (reader.ready()) {
						sb.append((char) reader.read());
					}
				}
			}
			return BlockStatement.toFunction(new SubString(sb.toString()));
		} catch (SyntaxError e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FunctionData parseToFunction(String script) {
		try {
			return BlockStatement.toFunction(new SubString(script));
		} catch (SyntaxError e) {
			e.printStackTrace();
		}
		return null;
	}
}
