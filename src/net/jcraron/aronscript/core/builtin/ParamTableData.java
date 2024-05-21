package net.jcraron.aronscript.core.builtin;

import java.util.HashMap;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.Table;

public class ParamTableData extends Table {

	public ParamTableData() {
		super(new HashMap<>());
	}

	@Override
	public ReturnThrowDataSet __assign__(Data key, Data value) {
		this.map.put(key, value);
		return this.returnThis();
	}

	public Table toNormal() {
		return new Table(this.map);
	}
}
