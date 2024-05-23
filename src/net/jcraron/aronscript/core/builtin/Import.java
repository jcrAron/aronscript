package net.jcraron.aronscript.core.builtin;

import java.io.File;
import java.io.IOException;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;
import net.jcraron.aronscript.core.base.NumberData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.executor.CoreConfigs;
import net.jcraron.aronscript.parser.AronScriptParser;

public class Import extends Data {
	public final static Data INSTANCE = new Import();
	public final static ClassData CLASS = new ClassData(Import.class);
	private final static String PREFIX_STDLIB = "stdlib";

	private Import() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		CoreConfigs config = CoreConfigs.getCurrentConfig();
		if (!Data.isNull(code)) {
			return ReturnThrowDataSet.throwData(StringData.valueOf("error"));
		} else if (!config.isEnableImport()) {
			return ReturnThrowDataSet.throwData(StringData.valueOf("import is disabled"));
		}
		ReturnThrowDataSet mainSet = args.__index__(NumberData.valueOf(0));
		if (mainSet.isThrow) {
			return mainSet;
		}
		String main = mainSet.data.toString();
		Data data = config.getPreloadPackage().get(main);
		if (data != null) {
			return data.returnThis();
		} else if (main.startsWith(PREFIX_STDLIB)) {
			return importStdlib(main.toString());
		} else {
			return importFile(main.toString());
		}
	}

	private ReturnThrowDataSet importStdlib(String main) {
		String root = CoreConfigs.getCurrentConfig().getStandardLib();
		File file = new File(root, main);
		if (!file.exists()) {
			throw new RuntimeException("not found package: " + file.getAbsolutePath());
		}
		Data func = Data.NULL;
		try {
			func = AronScriptParser.parseToFunction(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func.__apply__(new Table(), null);
	}

	private ReturnThrowDataSet importFile(String main) {
		String root = CoreConfigs.getCurrentConfig().getPackagePath();
		File file = new File(root, main);
		if (!file.exists()) {
			throw new RuntimeException("not found package: " + file.getAbsolutePath());
		}
		Data func = Data.NULL;
		try {
			func = AronScriptParser.parseToFunction(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func.__apply__(new Table(), null);
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return Import.CLASS.returnThis();
	}
}
