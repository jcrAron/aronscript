package net.jcraron.aronscript.core.base;

import java.util.Objects;

import net.jcraron.aronscript.core.CommonException;
import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public final class StringData extends Data {
	public final static ClassData CLASS = new ClassData(StringData.class);
	public final String string;

	public StringData(String str) {
		this.string = str;
	}

	@Override
	public final ReturnThrowDataSet __class__() {
		return StringData.CLASS.returnThis();
	}

	public static StringData valueOf(String str) {
		return new StringData(str);
	}

	static ReturnThrowDataSet returnValue(String string) {
		return StringData.valueOf(string).returnThis();
	}

	@Override
	public ReturnThrowDataSet __add__(Data next) {
		if (!(next instanceof StringData stringata)) {
			return ReturnThrowDataSet.throwData(CommonException.CAST_FAILD);
		}
		StringBuilder sb = new StringBuilder(this.string.length() + stringata.string.length());
		sb.append(this.string);
		sb.append(stringata.string);
		return StringData.returnValue(sb.toString());
	}

	@Override
	public ReturnThrowDataSet __multi__(Data times) {
		if (!(times instanceof NumberData numberData)) {
			return ReturnThrowDataSet.throwData(CommonException.CAST_FAILD);
		}
		StringBuilder sb = new StringBuilder(((int) numberData.value) * this.string.length());
		for (int i = 0; i < numberData.value; i++) {
			sb.append(this.string);
		}
		return StringData.returnValue(sb.toString());
	}

	@Override
	public String toString() {
		return '"' + string + '"';
	}

	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringData other = (StringData) obj;
		return Objects.equals(string, other.string);
	}
}
