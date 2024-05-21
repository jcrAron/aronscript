package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;

public class NewTableData extends Data {
	public final static Data INSTANCE = new NewTableData();
	public final static ClassData CLASS = new ClassData(NewTableData.class);

	private NewTableData() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		if (!Data.isNull(code)) {
			ReturnThrowDataSet set = code.__apply__(args, null);
			if (set.isThrow) {
				return set;
			}
		}
		return args.returnThis();
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return NewTableData.CLASS.returnThis();
	}
}
