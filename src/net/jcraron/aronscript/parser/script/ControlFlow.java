package net.jcraron.aronscript.parser.script;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.util.SubString;

public final class ControlFlow {
	public final ControlFlowType type;
	private final Object obj;

	public ControlFlow(ControlFlowType type, Object obj) {
		this.type = type;
		this.obj = obj;
	}

	public ReturnThrowDataSet toReturnThrowDataSet() {
		if (type == ControlFlowType.RETURN) {
			return ReturnThrowDataSet.returnData((Data) obj);
		} else if (type == ControlFlowType.THROW) {
			return ReturnThrowDataSet.throwData((Data) obj);
		} else {
			throw new RuntimeException("");
		}
	}

	public SubString toLabel() {
		if (type == ControlFlowType.BREAK || type == ControlFlowType.CONTINUE) {
			return (SubString) obj;
		} else {
			throw new RuntimeException("");
		}
	}

	public final static ControlFlow NONE = new ControlFlow(ControlFlowType.NONE, null);
	public final static ControlFlow IF_FALSE = new ControlFlow(ControlFlowType.IF_FALSE, null);

	public static ControlFlow BREAK(SubString label) {
		return new ControlFlow(ControlFlowType.BREAK, label);
	}

	public static ControlFlow CONTINUE(SubString label) {
		return new ControlFlow(ControlFlowType.CONTINUE, label);
	}

	public static ControlFlow RETURN(Data data) {
		return new ControlFlow(ControlFlowType.RETURN, data);
	}

	public static ControlFlow THROW(Data data) {
		return new ControlFlow(ControlFlowType.THROW, data);
	}
}
