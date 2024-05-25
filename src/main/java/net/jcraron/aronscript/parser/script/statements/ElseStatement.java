package net.jcraron.aronscript.parser.script.statements;

import java.util.Arrays;
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

public class ElseStatement implements Statement {
	private SubString label;
	private ValueStatement condition;
	private Statement codeBlock;

	public ElseStatement(SubString label, ValueStatement condition, Statement codeBlock) {
		this.label = label;
		this.condition = condition;
		this.codeBlock = codeBlock;
	}

	public ElseStatement(SubString label, Statement codeBlock) {
		this(label, null, codeBlock);
	}

	@Override
	public ControlFlow run(Data env) {
		if (condition != null) {
			ReturnThrowDataSet conSet = condition.getValue(env);
			if (conSet.isThrow) {
				return ControlFlow.THROW(conSet.data);
			} else if (conSet.data == BooleanData.FALSE) {
				return ControlFlow.IF_FALSE;
			} else if (conSet.data != BooleanData.TRUE) {
				return ControlFlow.THROW(StringData.valueOf("condition result must be boolean"));
			}
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
	static ElseStatement parse(SubString[] line, Set<CharSequence> labelSet, SubString label)
			throws SyntaxError {
		if (!line[0].contentEquals("else")) {
			throw new SyntaxError("");
		}
		CommonBlock commonBlock;
		ValueStatement condition;
		if (line[1].contentEquals("if")) {
			line = Arrays.copyOfRange(line, 1, line.length);
			commonBlock = CommonBlock.parse("if", true, false, false, true, line, labelSet);
			condition = OperatorStatement.parse(new SubString[] { commonBlock.roundBracket() });
		} else {
			commonBlock = CommonBlock.parse("else", true, false, false, false, line, labelSet);
			condition = null;
		}
		Statement codeBlock = commonBlock.block();
		return new ElseStatement(label, condition, codeBlock);
	}

	static boolean check(SubString[] line) {
		if (!line[0].contentEquals("else")) {
			return false;
		}
		if (line.length == 2) {
			return true;
		} else if (line.length == 4 && line[1].contentEquals("if")) {
			return true;
		}
		return false;
	}
}
