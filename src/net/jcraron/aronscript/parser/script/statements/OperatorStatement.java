package net.jcraron.aronscript.parser.script.statements;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.Operator;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.parser.script.Symbol;
import net.jcraron.aronscript.parser.script.ValueStatement;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubString;

public class OperatorStatement implements ValueStatement {

	private Deque<Object> list;
	final static Object ENV = "$ENV";

	public OperatorStatement(Deque<Object> list) {
		this.list = list;
	}

	@Override
	public ReturnThrowDataSet getValue(Data env) {
		System.out.println("value=> " + list.toString());
		Deque<Data> variablesStack = new LinkedList<>();
		Symbol skipMode = null;
		for (Object object : list) {
			if (object instanceof Symbol symbol) {
				if (symbol == skipMode) {
					skipMode = null;
					continue;
				} else if (symbol == Symbol.LOGICAL_OR || symbol == Symbol.LOGICAL_AND) {
					Data lastData = variablesStack.peekFirst();
					if (lastData != BooleanData.TRUE && lastData != BooleanData.FALSE) {
						throw new RuntimeException("");
					}
					continue;
				} else if (skipMode != null) {
					continue;
				} else if (symbol == Symbol.SKIP_UNTIL_LOGICAL_AND || symbol == Symbol.SKIP_UNTIL_LOGICAL_OR) {
					Data lastData = variablesStack.peekFirst();
					if (lastData != BooleanData.TRUE && lastData != BooleanData.FALSE) {
						throw new RuntimeException("");
					}
					if (lastData == BooleanData.TRUE && symbol == Symbol.SKIP_UNTIL_LOGICAL_OR) {
						skipMode = Symbol.LOGICAL_OR;
					} else if (lastData == BooleanData.FALSE && symbol == Symbol.SKIP_UNTIL_LOGICAL_AND) {
						skipMode = Symbol.LOGICAL_AND;
					} else {
						variablesStack.pop();
					}
					continue;
				}
				Data[] args = new Data[symbol.argLength - 1];
				Data self;
				for (int i = args.length - 1; i >= 0; i--) {
					args[i] = variablesStack.pop();
				}
				self = variablesStack.pop();
				if (symbol == Symbol.COMMA) {
					continue;
				}
				if (symbol.op == null) {
					throw new RuntimeException("");
				}
				if (self == null) {
					return ReturnThrowDataSet.throwData(StringData.valueOf("null pointer exception"));
				}
				ReturnThrowDataSet result;
				if (symbol.withAssign) {
					ReturnThrowDataSet firstArg = Data.operate(self, Operator.INDEX, new Data[] { args[0] });
					if (firstArg.isThrow) {
						return firstArg;
					}
					ReturnThrowDataSet secondArg = Data.operate(firstArg.data, symbol.op, new Data[] { args[1] });
					if (secondArg.isThrow) {
						return secondArg;
					}
					result = Data.operate(self, Operator.ASSIGN, new Data[] { args[0], secondArg.data });
				} else {
					result = Data.operate(self, symbol.op, args);
				}
				if (result.isThrow) {
					return result;
				}
				variablesStack.push(result.data);
			} else if (skipMode != null) {
				continue;
			} else if (object == ENV) {
				variablesStack.push(env);
			} else if (object instanceof Data data) {
				variablesStack.push(data);
			}
		}
		Data result = variablesStack.pop();
		System.out.println("result env: " + env.toString());
		System.out.println("result return: " + Objects.toString(result));
		return result != null ? result.returnThis() : ReturnThrowDataSet.RETURN_NULL;
	}

	static ValueStatement parse(SubString[] line) throws SyntaxError {
		Deque<Object> list = OperatorStatementParser.parse(line);
		return new OperatorStatement(list);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
