package net.jcraron.aronscript.parser;

import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.util.SubString;

public abstract class ScriptFunctionData extends FunctionData {
	private final SubString script;

	public ScriptFunctionData(SubString script) {
		this.script = script;
	}

	public SubString getOriginScript() {
		return script;
	}

	@Override
	public String toString() {
		return script.toString();
	}
}
