package net.jcraron.aronscript.parser.script.statements;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.core.base.NumberData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.core.special.CatcherData;
import net.jcraron.aronscript.core.special.DefineFunctionData;
import net.jcraron.aronscript.core.special.NewTableData;
import net.jcraron.aronscript.parser.script.CharDefine;
import net.jcraron.aronscript.parser.script.Symbol;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubArray;
import net.jcraron.aronscript.util.SubString;

class OperatorStatementParser {
	private final static Object ENV = OperatorStatement.ENV;

	static Deque<Object> parse(SubString[] line) throws SyntaxError {
		Deque<Object> ret = new LinkedList<>();
		new OperatorStatementParser(ret).parseLine(line);
		return ret;
	}

	/*--------------------------------------------------------------------------------------------------------------*/
	private Deque<Object> itemList;
	private int dataCount;

	private OperatorStatementParser(Deque<Object> itemList) {
		this.itemList = itemList;
		dataCount = 0;
	}

	private void cleanSymbolStack(Stack<Symbol> opStack, Symbol newestSymbol) throws SyntaxError {
		while (!opStack.isEmpty()) {
			Symbol peekSymbol = opStack.peek();
			int newestPriority = newestSymbol.priority;
			int LastPriority = peekSymbol.priority;
			if (newestPriority < LastPriority
					|| (newestSymbol.rightToLeft == Symbol.Constants.LEFT_TO_RIGHT && newestPriority == LastPriority)) {
				itemList.offer(opStack.pop());
			} else {
				break;
			}
		}
	}

	private void pushSymbol(Stack<Symbol> opStack, Symbol newestSymbol) throws SyntaxError {
		cleanSymbolStack(opStack, newestSymbol);
		if (newestSymbol == Symbol.LOGICAL_AND) {
			itemList.offer(Symbol.SKIP_UNTIL_LOGICAL_AND);
		} else if (newestSymbol == Symbol.LOGICAL_OR) {
			itemList.offer(Symbol.SKIP_UNTIL_LOGICAL_OR);
		}
		opStack.push(newestSymbol);
		if (newestSymbol == Symbol.COMMA) {
//			dataCount -= Symbol.COMMA.argLength;
		} else {
			dataCount = dataCount - newestSymbol.argLength + 1;
		}
	}

	private Object parseConst(SubString part) {
		if (part.contentEquals("env")) {
			return ENV;
		} else if (part.contentEquals("import")) {
			return null; // TODO
		} else if (part.contentEquals("table")) {
			return NewTableData.INSTANCE;
		} else if (part.contentEquals("function")) {
			return DefineFunctionData.INSTANCE;
		} else if (part.contentEquals("catcher")) {
			return CatcherData.INSTANCE;
		} else if (part.contentEquals("null")) {
			return Data.NULL;
		} else if (part.contentEquals("true")) {
			return BooleanData.TRUE;
		} else if (part.contentEquals("false")) {
			return BooleanData.FALSE;
		} else if (CharDefine.isValidOpeningBracket(part.charAt(0)) == CharDefine.BRACKETS_TYPE_QUOTATION) {
			return StringData.valueOf(StatementSpliterator.trimBrackets(part).toString());
		} else if (CharDefine.isNumber(part.charAt(0))) {
			return NumberData.valueOf(part.toString());
		} else {
			return null;
		}
	}

	private boolean isName(SubString part) {
		return !CharDefine.isNumber(part.charAt(0)) && CharDefine.isText(part.charAt(0));
	}

	private void addItem(Object item) {
		itemList.add(item);
		dataCount++;
	}

	private void parseOperatorPart(SubString part) throws SyntaxError {
		LinkedList<SubString[]> lines = StatementSpliterator.splitAllLines(StatementSpliterator.trimBrackets(part));
		if (lines.size() > 1) {
			throw new SyntaxError("");
		}
		new OperatorStatementParser(this.itemList).parseLine(lines.get(0));
		this.dataCount++;
	}

	private Data parseFunctionBlock(SubString part) throws SyntaxError {
		return BlockStatement.toFunction(StatementSpliterator.trimBrackets(part));
	}
	private Data parseCatcherBlock(SubString part) throws SyntaxError {
		return BlockStatement.toCatcherBlock(StatementSpliterator.trimBrackets(part));
	}

	private void parseTable(SubString part) throws SyntaxError {
		LinkedList<SubString[]> lines = StatementSpliterator.splitAllLines(StatementSpliterator.trimBrackets(part));
		if (lines.size() > 1) {
			throw new SyntaxError("");
		}
		Data table = newCallTable();
		SubString[] line = lines.get(0);
		int startIndex = 0;
		int endIndex = -1;
		int partCounter = 0;
		for (int index = 0; index < line.length; index++) {
			if (line[index].contentEquals(Symbol.COMMA.symbol)) {
				startIndex = endIndex + 1;
				endIndex = index;
				boolean hasAssign = parsePartOfTable(new SubArray<>(line).subarray(startIndex, endIndex), table,
						partCounter);
				if (!hasAssign && partCounter >= 0) {
					partCounter++;
				} else {
					partCounter = -1;
				}
			}
		}
		parsePartOfTable(new SubArray<>(line).subarray(endIndex + 1, line.length), table, partCounter);
		addItem(table);
	}

	/** @return true if the array include Symbol.ASSIGN symbol */
	private boolean parsePartOfTable(SubArray<SubString> array, Data baseTable, int partCount) throws SyntaxError {
		boolean hasAssign = false;
		Stack<Symbol> opStack = new Stack<>();
		if (array.index(1).contentEquals(Symbol.ASSIGN.symbol)) {
			hasAssign = true;
			SubString firstPart = array.index(0);
			addItem(baseTable);
			if (parseConst(firstPart) == null && isName(firstPart)) {
				addItem(StringData.valueOf(firstPart.toString()));
			} else if (CharDefine.isValidOpeningBracket(firstPart.charAt(0)) == CharDefine.BRACKETS_TYPE_SQUARE) {
				parseOperatorPart(firstPart);
			} else {
				throw new SyntaxError("");
			}
		} else {
			if (partCount < 0) {
				throw new SyntaxError("");
			}
			addItem(NumberData.valueOf(partCount));
			hasAssign = false;
		}
		pushSymbol(opStack, Symbol.ASSIGN);
		new OperatorStatementParser(this.itemList).parseLine(array.subarray(hasAssign ? 2 : 0).toArray());
		pushSymbol(opStack, Symbol.COMMA);
		cleanSymbolStack(opStack, Symbol.END);
		return hasAssign;
	}

	private Data newCallTable() {
		return new Table();
	}

	private void parseLine(SubString[] line) throws SyntaxError {
		boolean isSymbolAtLast = true;
		boolean isConstOrVarAtLast = false;
		Stack<Symbol> opStack = new Stack<>();
		for (int index = 0; index < line.length; index++) {
			SubString part = line[index];
			int[] bracketType = CharDefine.isValidOpeningBracket(part.charAt(0));
			Object constant = parseConst(part);
			boolean isIndexMode = !opStack.isEmpty() && opStack.peek() == Symbol.INDEX;
			if (isSymbolAtLast && constant == null && isName(part)) {
				// variable
				if (!isIndexMode) {
					addItem(ENV);
					addItem(StringData.valueOf(part.toString()));
					pushSymbol(opStack, Symbol.INDEX);
				} else {
					addItem(StringData.valueOf(part.toString()));
				}
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else if (isSymbolAtLast && !isIndexMode && constant != null) {
				// constant
				if (constant == CatcherData.INSTANCE) {
					if (index + 1 >= line.length) {
						throw new SyntaxError("");
					}
					SubString nextPart = line[index + 1];
					int[] nextPartBracketType = CharDefine.isValidOpeningBracket(nextPart.charAt(0));
					if (nextPartBracketType != CharDefine.BRACKETS_TYPE_CURLY) {
						throw new SyntaxError("");
					}
					addItem(CatcherData.INSTANCE);
					cleanSymbolStack(opStack, Symbol.APPLY);
					addItem(ENV);
					addItem(parseCatcherBlock(nextPart));
					pushSymbol(opStack, Symbol.APPLY);
					index++;
				} else {
					addItem(constant);
				}
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else if (Symbol.foundSymbolByString(part)) {
				// operator
				Symbol symbol = Symbol.getOperatorSymbol(part, isSymbolAtLast);
				if (symbol == null) {
					throw new SyntaxError("unknow symbol: " + part);
				}
				if (symbol == Symbol.ASSIGN || symbol.withAssign) {
					if (opStack.pop() != Symbol.INDEX) {
						throw new SyntaxError("invalid assign format");
					}
					dataCount++;
				}
				pushSymbol(opStack, symbol);
				isSymbolAtLast = true;
				isConstOrVarAtLast = false;
			} else if (isSymbolAtLast && bracketType == CharDefine.BRACKETS_TYPE_ROUND) {
				parseOperatorPart(part);
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else if (isConstOrVarAtLast && bracketType == CharDefine.BRACKETS_TYPE_ROUND) {
				cleanSymbolStack(opStack, Symbol.APPLY);
				parseTable(part);
				if (index + 1 >= line.length) {
					// add empty function
					addItem(Data.NULL);
				} else {
					SubString nextPart = line[index + 1];
					int[] nextPartBracketType = CharDefine.isValidOpeningBracket(nextPart.charAt(0));
					if (nextPartBracketType == CharDefine.BRACKETS_TYPE_CURLY) {
						addItem(parseFunctionBlock(nextPart));
						index++;
					} else {
						// add empty function
						addItem(Data.NULL);
					}
				}
				pushSymbol(opStack, Symbol.APPLY);
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else if (isConstOrVarAtLast && bracketType == CharDefine.BRACKETS_TYPE_CURLY) {
				addItem(newCallTable());
				cleanSymbolStack(opStack, Symbol.APPLY);
				addItem(parseFunctionBlock(part));
				pushSymbol(opStack, Symbol.APPLY);
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else if (isConstOrVarAtLast && bracketType == CharDefine.BRACKETS_TYPE_SQUARE) {
				cleanSymbolStack(opStack, Symbol.INDEX);
				parseOperatorPart(part);
				pushSymbol(opStack, Symbol.INDEX);
				isSymbolAtLast = false;
				isConstOrVarAtLast = true;
			} else {
				throw new SyntaxError("unknow part: " + part);
			}
			System.out.println("part= " + part);
			System.out.println("dataCount= " + dataCount);
			System.out.println("isSymbolAtLast= " + isSymbolAtLast);
			System.out.println("isConstOrVarAtLast= " + isConstOrVarAtLast);
			System.out.println("opStack= " + opStack);
		}
		cleanSymbolStack(opStack, Symbol.END);
		System.out.println("itemList= " + itemList);
		System.out.println("opStack= " + opStack);
		System.out.println("dataCount= " + dataCount);
		if (dataCount != 1) {
			throw new SyntaxError("data and operator is imbalance");
		}
		System.out.println("input= " + Arrays.toString(line));
		System.out.println("output= " + itemList.toString());
	}
}
