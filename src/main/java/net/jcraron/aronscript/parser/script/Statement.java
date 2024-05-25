package net.jcraron.aronscript.parser.script;

import net.jcraron.aronscript.core.Data;

@FunctionalInterface
public interface Statement {
	public ControlFlow run(Data env);
}
