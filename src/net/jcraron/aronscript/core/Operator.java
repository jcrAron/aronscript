package net.jcraron.aronscript.core;

import java.util.HashMap;
import java.util.Map;

import net.jcraron.aronscript.core.base.StringData;

//考慮是否應該實作，因為||和&&包含不執行後續語句的行為
//			 LOGICAL_OR("a||b","a","b");
//			 LOGICAL_AND("a&&b","a","b");
//			 a?b:c
//inc,dec 包含 assign操作，所以不支援，例如a++相當於a+=1
//		 POSTFIX_INC("a++","a");
//		 POSTFIX_DEC("a--","a");
//		 PREFIX_INC("++a","a");
//		 PREFIX_DEC("--a","a");
public enum Operator {
	HASH("__hash__", new String[0]),
	APPLY("__apply__", new String[] { "self", "args", "code" }),
	STRING("__string__", new String[] { "self" }),
	INDEX("__index__", new String[] { "self", "index" }),
	ASSIGN("__assign__", new String[] { "self", "index", "value" }),
	EQUAL("__equal__", new String[] { "self", "value" }),
	NOT_EQUAL("__notEqual__", new String[] { "self", "value" }),
	GREATER_THAN("__greaterThan__", new String[] { "self", "value" }),
	LESS_THAN("__lessThan__", new String[] { "self", "value" }),
	GREATER_EQUAL("__greaterEqual__", new String[] { "self", "value" }),
	LESS_EQUAL("__lessEqual__", new String[] { "self", "value" }),
	LOGICAL_NOT("__logicNot__", new String[] { "self" }),
	UNARY_PLUS("__unaryPlus__", new String[] { "self" }),
	UNARY_MINUS("__unaryMinus__", new String[] { "self" }),
	ADD("__add__", new String[] { "self", "value" }),
	SUB("__sub__", new String[] { "self", "value" }),
	MULTI("__multi__", new String[] { "self", "value" }),
	DIV("__div__", new String[] { "self", "value" }),
	MOD("__mod__", new String[] { "self", "value" }),
	RIGHT_SHIFT("__rightShift__", new String[] { "self", "zvalue" }),
	LEFT_SHIFT("__leftShift__", new String[] { "self", "value" }),
	AND("__and__", new String[] { "self", "value" }),
	OR("__or__", new String[] { "self", "value" }),
	XOR("__xor__", new String[] { "self", "value" }),
	BITWISE_NOT("__bitwiseNot__", new String[] { "self", "value" }),
	POW("__pow__", new String[] { "self", "value" });

	private final static Map<String, Operator> map;
	static {
		map = new HashMap<>();
		for (Operator meta : Operator.values()) {
			map.put(meta.key, meta);
		}
	}

	public static Operator find(String key) {
		return map.get(key);
	}

	public final String key;
	private final StringData[] params;

	Operator(String key, String[] params) {
		this.key = key;
		this.params = new StringData[params.length];
		for (int i = 0; i < params.length; i++) {
			this.params[i] = StringData.valueOf(params[i]);
		}
	}

	public String getKey() {
		return key;
	}

	public StringData[] getParams() {
		return params;
	}
}