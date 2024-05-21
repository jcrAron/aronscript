package net.jcraron.aronscript.parser.script.statements;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.parser.ScriptFunctionData;
import net.jcraron.aronscript.parser.script.ControlFlow;
import net.jcraron.aronscript.parser.script.Statement;
import net.jcraron.aronscript.parser.script.ValueStatement;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubString;

public class BlockStatement implements Statement {
	private List<Statement> serialStatements;

	public BlockStatement(List<Statement> serialStatements) {
		this.serialStatements = serialStatements;
	}

	@Override
	public ControlFlow run(Data env) {
		boolean if_false_state = false;
		for (Statement st : serialStatements) {
			if (st instanceof ElseStatement) {
				if (!if_false_state) {
					continue;
				}
			}
			if_false_state = false;
			ControlFlow result = st.run(env);
			switch (result.type) {
			case IF_FALSE:
				if_false_state = true;
				break;
			case NONE:
				break;
			case BREAK:
			case CONTINUE:
			case RETURN:
			case THROW:
				return result;
			}
		}
		return ControlFlow.NONE;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	private static Statement breakStatment(SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		SubString label = line.length <= 1 ? null : line[1];
		if (!labelSet.contains(label)) {
			throw new SyntaxError("not found the label: " + label);
		}
		return (data) -> ControlFlow.BREAK(label);
	}

	private static Statement continueStatment(SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		SubString label = line.length <= 1 ? null : line[1];
		if (!labelSet.contains(label)) {
			throw new SyntaxError("not found the label: " + label);
		}
		return (data) -> ControlFlow.CONTINUE(label);
	}

	private static boolean isBreakStatment(SubString[] line) {
		return line[0].contentEquals("break") && line.length <= 2;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private static boolean isContinueStatment(SubString[] line) {
		return line[0].contentEquals("continue") && line.length <= 2;
	}

	private static boolean isReturnStatment(SubString[] line) {
		return line[0].contentEquals("return");
	}

	private static boolean isThrowStatment(SubString[] line) {
		return line[0].contentEquals("throw");
	}

	private static Statement returnStatment(SubString[] line) throws SyntaxError {
		ValueStatement valueFunc = OperatorStatement.parse(Arrays.copyOfRange(line, 1, line.length));
		return new Statement() {
			@Override
			public ControlFlow run(Data data) {
				ReturnThrowDataSet valueResult = valueFunc.getValue(data);
				if (valueResult.isThrow) {
					return ControlFlow.THROW(valueResult.data);
				}
				return ControlFlow.RETURN(valueResult.data);
			}
		};
	}

	private static Statement throwStatment(SubString[] line) throws SyntaxError {
		ValueStatement valueFunc = OperatorStatement.parse(Arrays.copyOfRange(line, 1, line.length));
		return new Statement() {
			@Override
			public ControlFlow run(Data data) {
				ReturnThrowDataSet valueResult = valueFunc.getValue(data);
				return ControlFlow.THROW(valueResult.data);
			}
		};
	}

	public static ScriptFunctionData toFunction(SubString string) throws SyntaxError {
		Statement func = toRunnable(string, false, false, true, true, new HashSet<>());
		ScriptFunctionData data = new ScriptFunctionData(string) {
			@Override
			public ReturnThrowDataSet __apply__(Data args, Data code) {
				if (!Data.isNull(code)) {
					return ReturnThrowDataSet.throwData(StringData.valueOf("call function with code is not supported"));
				}
				ControlFlow result = func.run(args);
				switch (result.type) {
				case NONE:
					return ReturnThrowDataSet.RETURN_NULL;
				case RETURN:
				case THROW:
					return result.toReturnThrowDataSet();
				case BREAK:
				case CONTINUE:
				default:
					throw new RuntimeException();
				}
			}
		};
		return data;
	}

	static ScriptFunctionData toCatcherBlock(SubString string) throws SyntaxError {
		Statement func = toRunnable(string, false, false, false, true, new HashSet<>());
		ScriptFunctionData data = new ScriptFunctionData(string) {
			@Override
			public ReturnThrowDataSet __apply__(Data args, Data code) {
				if (!Data.isNull(code)) {
					throw new RuntimeException("run catcher with code is not supported");
				}
				ControlFlow result = func.run(args);
				switch (result.type) {
				case NONE:
					return ReturnThrowDataSet.returnData(null);
				case THROW:
					return ReturnThrowDataSet.returnData(result.toReturnThrowDataSet().data);
				case RETURN:
				case BREAK:
				case CONTINUE:
				default:
					throw new RuntimeException();
				}
			}
		};
		return data;
	}

	static Statement toRunnable(SubString string, boolean supportBreak, boolean supportContinue, boolean supportReturn,
			boolean supportThrow, Set<CharSequence> labelSet) throws SyntaxError {
		LinkedList<SubString[]> lines = StatementSpliterator.splitAllLines(string);
		LinkedList<Statement> serial = new LinkedList<>();
		SubString ifStatementLabel = null;
		boolean hasIfStatement = false;
		for (SubString[] line : lines) {
			Statement code = null;
			boolean isElseStatement = false;
			if (ForStatement.check(line)) {
				code = ForStatement.parse(line, labelSet);
			} else if (IfStatement.check(line)) {
				code = IfStatement.parse(line, labelSet);
				ifStatementLabel = ((IfStatement) code).getLabel();
				hasIfStatement = true;
				isElseStatement = true;
			} else if (ElseStatement.check(line)) {
				if (!hasIfStatement) {
					throw new RuntimeException("");
				}
				isElseStatement = true;
				code = ElseStatement.parse(line, labelSet, ifStatementLabel);
			} else if (WhileStatement.check(line)) {
				code = WhileStatement.parse(line, labelSet);
			} else if (isContinueStatment(line)) {
				if (!supportContinue) {
					throw new SyntaxError("not support continue statement");
				}
				code = continueStatment(line, labelSet);
			} else if (isBreakStatment(line)) {
				if (!supportBreak) {
					throw new SyntaxError("not support break statement");
				}
				code = breakStatment(line, labelSet);
			} else if (isReturnStatment(line)) {
				if (!supportReturn) {
					throw new SyntaxError("not support return statement");
				}
				code = returnStatment(line);
			} else if (isThrowStatment(line)) {
				if (!supportThrow) {
					throw new SyntaxError("not support break statement");
				}
				code = throwStatment(line);
			} else {
				code = OperatorStatement.parse(line);
			}
			if (!isElseStatement) {
				ifStatementLabel = null;
				hasIfStatement = false;
			}
			serial.add(code);
		}
		return new BlockStatement(serial);
	}
}
