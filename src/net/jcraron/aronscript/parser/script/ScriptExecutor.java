package net.jcraron.aronscript.parser.script;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.parser.serialcode.BinaryCodesExecutor;

public class ScriptExecutor {

	private String script;

	public ReturnThrowDataSet execute(Data env) {
		return new BinaryCodesExecutor(null).executeAll(null);
	}

	public SerialCodes com(Data env) {
		return null;
	}
}
