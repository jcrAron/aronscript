package net.jcraron.aronscript.parser.script;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

@FunctionalInterface
public interface ValueStatement extends Statement {

	public default ControlFlow run(Data env) {
		ReturnThrowDataSet set = this.getValue(env);
		if (set.isThrow) {
			return ControlFlow.THROW(set.data);
		}
		return ControlFlow.NONE;
	}

	public ReturnThrowDataSet getValue(Data env);

}
