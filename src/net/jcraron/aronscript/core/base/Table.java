package net.jcraron.aronscript.core.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public final class Table extends Data {
	public final static ClassData DEFAULT_CLASS = new ClassData(BooleanData.class);
	private Map<Data, Data> map;

	public Table(Map<Data, Data> map) {
		this.map = map;
	}

	public Table() {
		this(new HashMap<>());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		for (Entry<Data, Data> entry : map.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append(":");
			stringBuilder.append(entry.getValue());
			stringBuilder.append(", ");
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return Table.DEFAULT_CLASS.returnThis();
	}

	@Override
	public int hashCode() {
		return Objects.hash(map);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		return Objects.equals(map, other.map);
	}

	public ReturnThrowDataSet __index__(Data key) {
		return ReturnThrowDataSet.returnData(this.map.get(key));
	}

	public ReturnThrowDataSet __assign__(Data key, Data value) {
		return ReturnThrowDataSet.returnData(this.map.put(key, value));
	}
}