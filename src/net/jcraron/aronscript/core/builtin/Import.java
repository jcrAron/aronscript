package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.executor.ExecutorConfig;

public class Import extends Data {
	public final static Data INSTANCE = new Import();
	public final static ClassData CLASS = new ClassData(Import.class);

	private Import() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		if (!Data.isNull(code)) {
			return ReturnThrowDataSet.throwData(StringData.valueOf("error"));
		} else if (!ExecutorConfig.getCurrentConfig().isEnableImport()) {
			return ReturnThrowDataSet.throwData(StringData.valueOf("import is disabled"));
		}
		// TODO

		return args.returnThis();
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return Import.CLASS.returnThis();
	}
}
