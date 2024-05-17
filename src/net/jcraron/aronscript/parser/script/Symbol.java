package net.jcraron.aronscript.parser.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.jcraron.aronscript.core.Operator;
import net.jcraron.aronscript.util.SubString;

public enum Symbol {
	TO_ENV("..."),
	COMMA(",", 0, Constants.LEFT_TO_RIGHT, 1, false, null),
	END(";"),
	SKIP_UNTIL_LOGICAL_AND(null, 14, Constants.LEFT_TO_RIGHT, 1, false, null),
	SKIP_UNTIL_LOGICAL_OR(null, 14, Constants.LEFT_TO_RIGHT, 1, false, null),
	INDEX(".", 14, Constants.LEFT_TO_RIGHT, 2, false, Operator.INDEX),
	APPLY(null, 14, Constants.LEFT_TO_RIGHT, 3, false, Operator.APPLY),

	POW("**", 13, Constants.LEFT_TO_RIGHT, 2, false, Operator.POW),
	LOGICAL_NOT("!", 12, Constants.RIGHT_TO_LEFT, 1, false, Operator.LOGICAL_NOT),
	UNARY_PLUS("+", 12, Constants.RIGHT_TO_LEFT, 1, false, Operator.UNARY_PLUS),
	UNARY_MINUS("-", 12, Constants.RIGHT_TO_LEFT, 1, false, Operator.UNARY_MINUS),
	BITWISE_NOT("~", 12, Constants.RIGHT_TO_LEFT, 1, false, Operator.BITWISE_NOT),
	MULTI("*", 11, Constants.LEFT_TO_RIGHT, 2, false, Operator.MULTI),
	DIV("/", 11, Constants.LEFT_TO_RIGHT, 2, false, Operator.DIV),
	MOD("%", 11, Constants.LEFT_TO_RIGHT, 2, false, Operator.MOD),
	ADD("+", 10, Constants.LEFT_TO_RIGHT, 2, false, Operator.ADD),
	SUB("-", 10, Constants.LEFT_TO_RIGHT, 2, false, Operator.SUB),
	RIGHT_SHIFT(">>", 9, Constants.LEFT_TO_RIGHT, 2, false, Operator.RIGHT_SHIFT),
	LEFT_SHIFT("<<", 9, Constants.LEFT_TO_RIGHT, 2, false, Operator.LEFT_SHIFT),
	GREATER_THAN(">", 8, Constants.LEFT_TO_RIGHT, 2, false, Operator.GREATER_THAN),
	LESS_THAN("<", 8, Constants.LEFT_TO_RIGHT, 2, false, Operator.LESS_THAN),
	GREATER_EQUAL(">=", 8, Constants.LEFT_TO_RIGHT, 2, false, Operator.GREATER_EQUAL),
	LESS_EQUAL("<=", 8, Constants.LEFT_TO_RIGHT, 2, false, Operator.LESS_EQUAL),
	EQUAL("==", 7, Constants.LEFT_TO_RIGHT, 2, false, Operator.EQUAL),
	NOT_EQUAL("!=", 7, Constants.LEFT_TO_RIGHT, 2, false, Operator.NOT_EQUAL),
	AND("&", 6, Constants.LEFT_TO_RIGHT, 2, false, Operator.AND),
	XOR("^", 5, Constants.LEFT_TO_RIGHT, 2, false, Operator.XOR),
	OR("|", 4, Constants.LEFT_TO_RIGHT, 2, false, Operator.OR),
	LOGICAL_AND("&&", 3, Constants.LEFT_TO_RIGHT, 2, false, null),
	LOGICAL_OR("||", 2, Constants.LEFT_TO_RIGHT, 2, false, null),

	ASSIGN("=", 1, Constants.RIGHT_TO_LEFT, 3, false, Operator.ASSIGN),
	ASSIGN_LOGICAL_NOT("!=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.LOGICAL_NOT),
	ASSIGN_PLUS("+=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.ADD),
	ASSIGN_SUB("-=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.SUB),
	ASSIGN_RIGHT_SHIFT(">>=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.RIGHT_SHIFT),
	ASSIGN_LEFT_SHIFT("<<=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.LEFT_SHIFT),
	ASSIGN_AND("&=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.AND),
	ASSIGN_OR("|=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.OR),
	ASSIGN_XOR("^=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.XOR),
	ASSIGN_BITWISE_NOT("~=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.BITWISE_NOT),
	ASSIGN_POW("**=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.POW),
	ASSIGN_MULTI("*=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.MULTI),
	ASSIGN_DIV("/=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.DIV),
	ASSIGN_MOD("%=", 1, Constants.RIGHT_TO_LEFT, 3, true, Operator.MOD);

	public static class Constants {
		public final static boolean RIGHT_TO_LEFT = true;
		public final static boolean LEFT_TO_RIGHT = false;
	}

	public final String symbol;
	public final int priority;
	public final boolean rightToLeft;
	public final Operator op;
	public final int argLength;
	public final boolean withAssign;
	public final boolean specialSymbol;

	Symbol(String symbol) {
		this(symbol, -1, false, -1, false, null, true);
	}

	Symbol(String symbol, int priority, boolean rightToLeft, int argLength, boolean withAssignOperator, Operator op) {
		this(symbol, priority, rightToLeft, argLength, withAssignOperator, op, false);
	}

	Symbol(String symbol, int priority, boolean rightToLeft, int argLength, boolean withAssignOperator, Operator op,
			boolean specialSymbol) {
		this.symbol = symbol;
		this.priority = priority;
		this.rightToLeft = rightToLeft;
		this.argLength = argLength;
		this.withAssign = withAssignOperator;
		this.op = op;
		this.specialSymbol = specialSymbol;
	}

	public final static Set<Integer> SYMBOL_CHARS;
	private final static Map<SubString, ArrayList<Symbol>> SYMBOLS;
	static {
		SYMBOL_CHARS = new HashSet<>();
		SYMBOLS = new HashMap<>();
		for (Symbol op : Symbol.values()) {
			if (op.symbol == null) {
				continue;
			}
			SubString opSymbol = new SubString(op.symbol);
			ArrayList<Symbol> possibleSymbols = SYMBOLS.get(opSymbol);
			if (possibleSymbols == null) {
				possibleSymbols = new ArrayList<>();
				SYMBOLS.put(opSymbol, possibleSymbols);
			}
			possibleSymbols.add(op);
			for (int i = 0; i < opSymbol.length(); i++) {
				SYMBOL_CHARS.add((int) opSymbol.charAt(i));
			}
		}
	}

	public static boolean foundSymbolByString(SubString string) {
		return SYMBOLS.containsKey(string);
	}

	public static Symbol getOperatorSymbol(SubString string, boolean isUnary) {
		ArrayList<Symbol> list = SYMBOLS.get(string);
		if (list == null) {
			return null;
		}
		for (Symbol sym : list) {
			if (sym.specialSymbol) {
				continue;
			} else if (isUnary && sym.argLength == 1) {
				return sym;
			} else if (!isUnary && sym.argLength != 1) {
				return sym;
			}
		}
		return null;
	}

}
