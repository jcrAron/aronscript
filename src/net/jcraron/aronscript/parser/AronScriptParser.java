package net.jcraron.aronscript.parser;

import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.parser.script.statements.BlockStatement;
import net.jcraron.aronscript.util.SubString;

public class AronScriptParser {
	public static FunctionData parseToFunction(String script) {
		try {
			return BlockStatement.toFunction(new SubString(script));
		} catch (SyntaxError e) {
			e.printStackTrace();
		}
		return null;
	}
}
