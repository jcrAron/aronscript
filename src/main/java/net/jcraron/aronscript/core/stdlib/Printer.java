package net.jcraron.aronscript.core.stdlib;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.NumberData;

public class Printer extends Data {
	public final static Printer INSTANCE = new Printer();

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		StringBuilder sb = new StringBuilder();
		boolean hasAnyItem = false;
		for (int i = 0;; i++) {
			ReturnThrowDataSet set = args.__index__(NumberData.valueOf(i));
			if (set.isThrow) {
				return set;
			} else if (Data.isNull(set.data)) {
				break;
			}
			if (hasAnyItem) {
				sb.append(", ");
			}
			sb.append(set.data);
			hasAnyItem = true;
		}
		System.out.println(sb.toString());
		return ReturnThrowDataSet.RETURN_NULL;
	}
}
