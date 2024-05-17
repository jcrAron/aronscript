package net.jcraron.aronscript.parser.script.statements;

import java.util.LinkedList;
import java.util.Set;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.parser.script.ControlFlow;
import net.jcraron.aronscript.parser.script.Statement;
import net.jcraron.aronscript.parser.script.ValueStatement;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubString;

public class ForStatement implements Statement {
	private SubString label;
	private ValueStatement init;
	private ValueStatement condition;
	private ValueStatement afterthought;
	private Statement codeBlock;

	public ForStatement(SubString label, ValueStatement init, ValueStatement condition, ValueStatement afterthought,
			Statement codeBlock) {
		this.label = label;
		this.init = init;
		this.condition = condition;
		this.afterthought = afterthought;
		this.codeBlock = codeBlock;
	}

	@Override
	public ControlFlow run(Data env) {
		ReturnThrowDataSet initResult = init.getValue(env);
		if (initResult.isThrow) {
			return ControlFlow.THROW(initResult.data);
		}
		loop: while (true) {
			ReturnThrowDataSet conSet = condition.getValue(env);
			if (conSet.isThrow) {
				return ControlFlow.THROW(conSet.data);
			} else if (conSet.data == BooleanData.FALSE) {
				return ControlFlow.NONE;
			} else if (conSet.data != BooleanData.TRUE) {
				return ControlFlow.THROW(StringData.valueOf("condition result must be boolean"));
			}
			ControlFlow blockResult = codeBlock.run(env);
			sw: switch (blockResult.type) {
			case BREAK:
				if (!SubString.contentEquals(blockResult.toLabel(), label)) {
					return blockResult;
				}
				break loop;
			case CONTINUE:
				if (!SubString.contentEquals(blockResult.toLabel(), label)) {
					return blockResult;
				}
				break sw;
			case IF_FALSE:
			case NONE:
				break sw;
			case RETURN:
			case THROW:
				return blockResult;
			}
			ReturnThrowDataSet afterResult = afterthought.getValue(env);
			if (afterResult.isThrow) {
				return ControlFlow.THROW(afterResult.data);
			}
		}
		return ControlFlow.NONE;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	static Statement parse(SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		CommonBlock commonBlock = CommonBlock.parse("for", true, true, true, true, line, labelSet);
		Statement codeBlock = commonBlock.block();
		SubString label = commonBlock.label();
		LinkedList<SubString[]> roundBracketLines = StatementSpliterator.splitAllLines(commonBlock.roundBracket());
		ValueStatement init = OperatorStatement.parse(roundBracketLines.get(0));
		ValueStatement condition = OperatorStatement.parse(roundBracketLines.get(1));
		ValueStatement afterthought = OperatorStatement.parse(roundBracketLines.get(2));
		return new ForStatement(label, init, condition, afterthought, codeBlock);
	}

	static boolean check(SubString[] line) throws SyntaxError {
		if (line.length == 3 && line[0].contentEquals("for")) {
			return true;
		} else if (line.length == 5 && line[2].contentEquals("for")) {
			return true;
		} else {
			return false;
		}
	}
}
