package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;

public class CatcherData extends Data {
	public final static Data INSTANCE = new CatcherData();
	public final static ClassData CLASS = new ClassData(CatcherData.class);

	private CatcherData() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		ReturnThrowDataSet set = code.__apply__(args, null);
		return set.isThrow ? ReturnThrowDataSet.returnData(set.data) : ReturnThrowDataSet.RETURN_NULL;
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return CatcherData.CLASS.returnThis();
	}
}
