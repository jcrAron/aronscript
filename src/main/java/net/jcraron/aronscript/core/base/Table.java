package net.jcraron.aronscript.core.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public class Table extends Data {
	public final static ClassData DEFAULT_CLASS = new ClassData(BooleanData.class);
	protected Map<Data, Data> map;

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
		if (map.size() > 0) {
			stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		}
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
		return ReturnThrowDataSet.returnData(this.map.getOrDefault(key, Data.NULL));
	}

	public ReturnThrowDataSet __assign__(Data key, Data value) {
		Data ret;
		if (Data.isNull(value)) {
			ret = this.map.remove(key);
		} else {
			ret = this.map.put(key, value);
		}
		return ReturnThrowDataSet.returnData(ret == null ? Data.NULL : ret);
	}
}