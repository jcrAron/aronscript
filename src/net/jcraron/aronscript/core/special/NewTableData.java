package net.jcraron.aronscript.core.special;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;

public class NewTableData extends Data {
	public final static Data INSTANCE = new NewTableData();
	public final static ClassData CLASS = new ClassData(CatcherData.class);

	private NewTableData() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		ReturnThrowDataSet set = code.__apply__(args, null);
		return set.isThrow ? set.data.returnThis() : ReturnThrowDataSet.RETURN_NULL;
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return CatcherData.CLASS.returnThis();
	}
}
