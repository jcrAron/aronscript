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

public class WhileStatement implements Statement {
	private SubString label;
	private ValueStatement condition;
	private Statement codeBlock;

	public WhileStatement(SubString label, ValueStatement condition, Statement codeBlock) {
		this.label = label;
		this.condition = condition;
		this.codeBlock = codeBlock;
	}

	@Override
	public ControlFlow run(Data env) {
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
				continue loop;
			case IF_FALSE:
			case NONE:
				break sw;
			case RETURN:
			case THROW:
				return blockResult;
			}
		}
		return ControlFlow.NONE;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	static boolean check(SubString[] line) throws SyntaxError {
		if (line.length == 3 && line[0].contentEquals("while")) {
			return true;
		} else if (line.length == 5 && line[2].contentEquals("while")) {
			return true;
		} else {
			return false;
		}
	}

	static Statement parse(SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		CommonBlock commonBlock = CommonBlock.parse("while", true, true, true, true, line, labelSet);
		SubString label = commonBlock.label();
		Statement codeBlock = commonBlock.block();
		LinkedList<SubString[]> subLines = StatementSpliterator.splitAllLines(commonBlock.roundBracket());
		if (subLines.size() != 1) {
			throw new SyntaxError("");
		}
		ValueStatement condition = OperatorStatement.parse(subLines.get(0));
		return new WhileStatement(label, condition, codeBlock);
	}
}
