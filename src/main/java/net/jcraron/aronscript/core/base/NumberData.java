package net.jcraron.aronscript.core.base;

import java.util.Objects;

import net.jcraron.aronscript.core.CommonException;
import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public final class NumberData extends Data {
	public final static ClassData CLASS = new ClassData(NumberData.class);

	public final double value;

	private NumberData(double value) {
		this.value = value;
	}

	@Override
	public final ReturnThrowDataSet __class__() {
		return NumberData.CLASS.returnThis();
	}

	public static NumberData valueOf(double value) {
		return new NumberData(value);
	}

	public static NumberData valueOf(String value) {
		return new NumberData(Double.valueOf(value));
	}

	static ReturnThrowDataSet returnValue(double value) {
		return NumberData.valueOf(value).returnThis();
	}

	@Override
	public ReturnThrowDataSet __add__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(this.value + numberData.value);
	}

	@Override
	public ReturnThrowDataSet __sub__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(this.value - numberData.value);
	}

	@Override
	public ReturnThrowDataSet __multi__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(this.value * numberData.value);
	}

	@Override
	public ReturnThrowDataSet __div__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(this.value / numberData.value);
	}

	@Override
	public ReturnThrowDataSet __mod__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) % ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __rightShift__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) >> ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __leftShift__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) << ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __and__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) & ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __or__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) | ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __xor__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(((long) this.value) ^ ((long) numberData.value));
	}

	@Override
	public ReturnThrowDataSet __bitwiseNot__() {
		return NumberData.returnValue(~((long) this.value));
	}

	@Override
	public ReturnThrowDataSet __pow__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return NumberData.returnValue(Math.pow(this.value, numberData.value));
	}

	@Override
	public ReturnThrowDataSet __equal__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value == numberData.value);
	}

	@Override
	public ReturnThrowDataSet __notEqual__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value != numberData.value);
	}

	@Override
	public ReturnThrowDataSet __greaterThan__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value > numberData.value);
	}

	@Override
	public ReturnThrowDataSet __greaterEqual__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value >= numberData.value);
	}

	@Override
	public ReturnThrowDataSet __lessThan__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value < numberData.value);
	}

	@Override
	public ReturnThrowDataSet __lessEqual__(Data num) {
		if (!(num instanceof NumberData numberData)) {
			return CommonException.CAST_FAILD.throwThis();
		}
		return BooleanData.returnValue(this.value <= numberData.value);
	}

	@Override
	public ReturnThrowDataSet __unaryPlus__() {
		return ReturnThrowDataSet.returnData(this);
	}

	@Override
	public ReturnThrowDataSet __unaryMinus__() {
		return NumberData.returnValue(-this.value);
	}

	@Override
	public ReturnThrowDataSet __hash__() {
		return ReturnThrowDataSet.returnData(this);
	}

	@Override
	public String toString() {
		return Double.toString(value);
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
		NumberData other = (NumberData) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}
}
