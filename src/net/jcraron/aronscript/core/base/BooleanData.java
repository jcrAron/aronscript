package net.jcraron.aronscript.core.base;

import java.util.Objects;

import net.jcraron.aronscript.core.CommonException;
import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public final class BooleanData extends Data {
	public final static BooleanData TRUE = new BooleanData(true);
	public final static BooleanData FALSE = new BooleanData(false);
	public final static ClassData CLASS = new ClassData(BooleanData.class);
	public final boolean value;

	private BooleanData(boolean value) {
		this.value = value;
	}

	@Override
	public final ReturnThrowDataSet __class__() {
		return BooleanData.CLASS.returnThis();
	}

	public static BooleanData valueOf(boolean value) {
		return value ? BooleanData.TRUE : BooleanData.FALSE;
	}

	static ReturnThrowDataSet returnValue(boolean value) {
		return BooleanData.valueOf(value).returnThis();
	}

	@Override
	public ReturnThrowDataSet __and__(Data bool) {
		if (!(bool instanceof BooleanData boolData)) {
			return ReturnThrowDataSet.throwData(CommonException.CAST_FAILD);
		}
		return ReturnThrowDataSet.returnData(!this.value ? BooleanData.FALSE : boolData);
	}

	@Override
	public ReturnThrowDataSet __or__(Data bool) {
		if (!(bool instanceof BooleanData boolData)) {
			return ReturnThrowDataSet.throwData(CommonException.CAST_FAILD);
		}
		return ReturnThrowDataSet.returnData(this.value ? BooleanData.TRUE : boolData);
	}

	@Override
	public ReturnThrowDataSet __xor__(Data bool) {
		if (!(bool instanceof BooleanData boolData)) {
			return ReturnThrowDataSet.throwData(CommonException.CAST_FAILD);
		}
		return ReturnThrowDataSet.returnData(BooleanData.valueOf(this.value ^ boolData.value));
	}

	@Override
	public ReturnThrowDataSet __logicNot__() {
		return ReturnThrowDataSet.returnData(this.value ? BooleanData.FALSE : BooleanData.TRUE);
	}

	@Override
	public ReturnThrowDataSet __bitwiseNot__() {
		return ReturnThrowDataSet.returnData(this.value ? BooleanData.FALSE : BooleanData.TRUE);
	}

	@Override
	public ReturnThrowDataSet __equal__(Data bool) {
		return ReturnThrowDataSet.returnData(this == bool ? BooleanData.TRUE : BooleanData.FALSE);
	}

	@Override
	public ReturnThrowDataSet __notEqual__(Data bool) {
		return ReturnThrowDataSet.returnData(this == bool ? BooleanData.FALSE : BooleanData.TRUE);
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanData other = (BooleanData) obj;
		return value == other.value;
	}
}
