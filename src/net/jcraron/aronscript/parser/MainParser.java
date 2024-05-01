package net.jcraron.aronscript.parser;

import java.util.function.Function;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.parser.scanner.Scanner;

public class MainParser {
	public Function<Data, Data> parse(String script) {
		Scanner scan = new Scanner(script);
		
		return null;
	}
}
