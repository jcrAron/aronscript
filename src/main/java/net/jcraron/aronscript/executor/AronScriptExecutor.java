package net.jcraron.aronscript.executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.parser.AronScriptParser;

public class AronScriptExecutor extends Thread {
	private final AronScriptRuntime config;

	public AronScriptExecutor(AronScriptRuntime config, FunctionData function, Data env) {
		super(() -> runFunction(function, env));
		this.config = config != null ? AronScriptRuntime.newDefaultConfig() : config;
	}

	public static AronScriptExecutor newExecutor(AronScriptRuntime config, File file, Data env)
			throws FileNotFoundException, IOException {
		FunctionData func = AronScriptParser.parseToFunction(file);
		return new AronScriptExecutor(config, func, env);
	}

	public static AronScriptExecutor newExecutor(AronScriptRuntime config, String script, Data env) {
		FunctionData func = AronScriptParser.parseToFunction(script);
		return new AronScriptExecutor(config, func, env);
	}

	public AronScriptRuntime getConfig() {
		return config;
	}

	public static void runFunction(FunctionData function, Data env) {
		ReturnThrowDataSet set = function.__apply__(function, env);
		if (set.isThrow) {
			set.panic();
		}
	}

}
