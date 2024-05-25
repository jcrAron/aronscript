package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;

public class CatcherBlock extends Data {
	public final static Data INSTANCE = new CatcherBlock();
	public final static ClassData CLASS = new ClassData(CatcherBlock.class);

	private CatcherBlock() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data env, Data code) {
		return code.__apply__(env, null);
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return CatcherBlock.CLASS.returnThis();
	}
}
