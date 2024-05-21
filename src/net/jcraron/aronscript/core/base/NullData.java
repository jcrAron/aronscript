package net.jcraron.aronscript.core.base;

import net.jcraron.aronscript.core.CommonException;
import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public final class NullData extends Data {
	public final static Data INSTANCE = new NullData();
	public final static ClassData CLASS = new ClassData(null);

	private NullData() {
	}

	@Override
	public String toString() {
		return "Data.NULL";
	}

	@Override
	public boolean equals(Object obj) {
		return obj == null || obj == this;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	/** @return Non-null */
	public ReturnThrowDataSet __class__() {
		return ReturnThrowDataSet.returnData(NullData.CLASS);
	}

	/** @return Non-null */
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __index__(Data key) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __assign__(Data key, Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __unaryMinus__() {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __unaryPlus__() {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __add__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __sub__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __multi__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __pow__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __div__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __mod__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __lessThan__(Data other) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __lessEqual__(Data other) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __greaterThan__(Data other) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __greaterEqual__(Data other) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __or__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __and__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __xor__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __logicNot__() {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __bitwiseNot__() {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __leftShift__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}

	/** @return Non-null */
	public ReturnThrowDataSet __rightShift__(Data value) {
		return CommonException.NULL_POINTER.throwThis();
	}
}
