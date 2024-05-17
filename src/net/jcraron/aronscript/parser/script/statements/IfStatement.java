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

public class IfStatement implements Statement {
	private SubString label;
	private ValueStatement condition;
	private Statement codeBlock;

	public IfStatement(SubString label, ValueStatement condition, Statement codeBlock) {
		this.label = label;
		this.condition = condition;
		this.codeBlock = codeBlock;
	}

	public SubString getLabel() {
		return label;
	}

	@Override
	public ControlFlow run(Data env) {
		ReturnThrowDataSet conSet = condition.getValue(env);
		if (conSet.isThrow) {
			return ControlFlow.THROW(conSet.data);
		} else if (conSet.data == BooleanData.FALSE) {
			return ControlFlow.IF_FALSE;
		} else if (conSet.data != BooleanData.TRUE) {
			return ControlFlow.THROW(StringData.valueOf("condition result must be boolean"));
		}
		ControlFlow blockResult = codeBlock.run(env);
		switch (blockResult.type) {
		case BREAK:
			if (!SubString.contentEquals(blockResult.toLabel(), label)) {
				return blockResult;
			} else {
				return ControlFlow.NONE;
			}
		case CONTINUE:
			throw new RuntimeException("");
		case IF_FALSE:
		case NONE:
			return ControlFlow.NONE;
		case RETURN:
		case THROW:
			return blockResult;
		default:
			break;
		}
		return ControlFlow.NONE;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	static IfStatement parse(SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		CommonBlock commonBlock = CommonBlock.parse("if", true, false, true, true, line, labelSet);
		SubString label = commonBlock.label();
		Statement codeBlock = commonBlock.block();
		LinkedList<SubString[]> subLines = StatementSpliterator.splitAllLines(commonBlock.roundBracket());
		if (subLines.size() != 1) {
			throw new SyntaxError("");
		}
		ValueStatement condition = OperatorStatement.parse(subLines.get(0));
		return new IfStatement(label, condition, codeBlock);
	}

	static boolean check(SubString[] line) {
		if (line.length == 3 && line[0].contentEquals("if")) {
			return true;
		} else if (line.length == 5 && line[2].contentEquals("if")) {
			return true;
		} else {
			return false;
		}
	}
}
