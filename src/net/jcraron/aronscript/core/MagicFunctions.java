package net.jcraron.aronscript.core;

import net.jcraron.aronscript.core.base.ClassData;

interface MagicFunctions {

	public static ReturnThrowDataSet operate(Data self, Operator op, Data... values) {
		switch (op) {
		case APPLY:
			return self.__apply__(values[0]);
		case AND:
			return self.__and__(values[0]);
		case LEFT_SHIFT:
			return self.__leftShift__(values[0]);
		case BITWISE_NOT:
			return self.__bitwiseNot__();
		case OR:
			return self.__or__(values[0]);
		case RIGHT_SHIFT:
			return self.__rightShift__(values[0]);
		case XOR:
			return self.__xor__(values[0]);
		case DIV:
			return self.__div__(values[0]);
		case EQUAL:
			return self.__equal__(values[0]);
		case INDEX:
			return self.__index__(values[0]);
		case GREATER_EQUAL:
			return self.__greaterEqual__(values[0]);
		case GREATER_THAN:
			return self.__greaterThan__(values[0]);
		case HASH:
			return self.__hash__();
		case LESS_EQUAL:
			return self.__lessEqual__(values[0]);
		case LESS_THAN:
			return self.__lessThan__(values[0]);
		case LOGICAL_NOT:
			return self.__logicNot__();
		case MOD:
			return self.__mod__(values[0]);
		case MULTI:
			return self.__multi__(values[0]);
		case NOT_EQUAL:
			return self.__notEqual__(values[0]);
		case ADD:
			return self.__add__(values[0]);
		case POW:
			return self.__pow__(values[0]);
		case ASSIGN:
			return self.__assign__(values[0], values[1]);
		case SUB:
			return self.__sub__(values[0]);
		case UNARY_MINUS:
			return self.__unaryMinus__();
		case UNARY_PLUS:
			return self.__unaryPlus__();
		case STRING:
			return self.__string__();
		}
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	@SuppressWarnings("unchecked")
	public default ReturnThrowDataSet __class__() {
		return ReturnThrowDataSet.returnData(new ClassData((Class<? extends Data>) this.getClass()));
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __string__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __hash__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __apply__(Data args) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __index__(Data key) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __assign__(Data key, Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __unaryMinus__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __unaryPlus__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __add__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __sub__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __multi__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __pow__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __div__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __mod__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __equal__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __notEqual__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __lessThan__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __lessEqual__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __greaterThan__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __greaterEqual__(Data other) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __or__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __and__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __xor__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __logicNot__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __bitwiseNot__() {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __leftShift__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

	/** @return Non-null */
	public default ReturnThrowDataSet __rightShift__(Data value) {
		return CommonException.OPERATOR_NOT_SUPPERTED.throwThis();
	}

}
