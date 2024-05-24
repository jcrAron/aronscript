package net.jcraron.aronscript.core;

import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.core.base.ClassData;
import net.jcraron.aronscript.core.base.NullData;
import net.jcraron.aronscript.core.base.NumberData;
import net.jcraron.aronscript.core.base.StringData;

public class Data implements MagicFunctions {
	public final static Data NULL = NullData.INSTANCE;

	public static boolean isNull(Data data) {
		return data == null || data == Data.NULL;
	}

	public ReturnThrowDataSet __class__() {
		return new ClassData(this.getClass()).returnThis();
	}

	public final static ReturnThrowDataSet operate(Data self, Operator op, Data... values) {
		return MagicFunctions.operate(self, op, values);
	}

	public final ReturnThrowDataSet operate(Operator op, Data... values) {
		return MagicFunctions.operate(this, op, values);
	}

	@Override
	public ReturnThrowDataSet __hash__() {
		return NumberData.valueOf(this.hashCode()).returnThis();
	}

	@Override
	public ReturnThrowDataSet __equal__(Data other) {
		return BooleanData.valueOf(this.equals(other)).returnThis();
	}

	@Override
	public ReturnThrowDataSet __notEqual__(Data other) {
		return BooleanData.valueOf(!this.equals(other)).returnThis();
	}

	@Override
	public ReturnThrowDataSet __string__() {
		return StringData.valueOf(this.toString()).returnThis();
	}

	public final ReturnThrowDataSet returnThis() {
		return ReturnThrowDataSet.returnData(this);
	}

	public final ReturnThrowDataSet throwThis() {
		return ReturnThrowDataSet.throwData(this);
	}
}
