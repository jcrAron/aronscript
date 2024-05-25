package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;

public class TableLib extends Data {
	public final static Data INSTANCE = new TableLib();
	public final static ClassData CLASS = new ClassData(TableLib.class);

	private TableLib() {
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
		return TableLib.CLASS.returnThis();
	}
}
