package net.jcraron.aronscript.core.base;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public abstract class FunctionData extends Data {

	public final static ClassData CLASS = new ClassData(FunctionData.class);

	@Override
	public abstract ReturnThrowDataSet __apply__(Data args, Data code);

	@Override
	public ReturnThrowDataSet __class__() {
		return FunctionData.CLASS.returnThis();
	}
}
