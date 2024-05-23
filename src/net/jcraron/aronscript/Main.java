package net.jcraron.aronscript;

import java.io.File;
import java.io.IOException;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.parser.AronScriptParser;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;

public class Main {
	public static void main(String... strings) throws SyntaxError {
		int test_count = 1;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < test_count; i++) {
			test_parse();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("parse time (ms): " + (endTime - startTime));
	}

	public static FunctionData test_parse() {
		FunctionData func = null;
		try {
			func = AronScriptParser.parseToFunction(
					new File("C:\\Users\\jcrAron\\eclipse-workspace\\aronscript\\example", "parse_example_1.as"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Data env = new Table();
		ReturnThrowDataSet set = func.__apply__(env, null);
		System.out.println("ENV->" + env.toString());
		System.out.println((set.isThrow ? "ERROR->" : "RETURN->") + set.data.toString());
		return func;
	}
}
