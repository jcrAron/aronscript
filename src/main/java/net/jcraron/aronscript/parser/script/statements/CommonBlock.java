package net.jcraron.aronscript.parser.script.statements;

import java.util.Set;

import net.jcraron.aronscript.parser.script.CharDefine;
import net.jcraron.aronscript.parser.script.Statement;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubString;

record CommonBlock(SubString label, SubString roundBracket, Statement block) {

	/*----------------------------------------------------------------------------------------------------------------*/
	static CommonBlock parse(String main, boolean supportBreak, boolean supportContinue, boolean hasLabel,
			boolean hasRoundBracket, SubString[] line, Set<CharSequence> labelSet) throws SyntaxError {
		SubString label;
		int mainIndex;
		if (line.length == 2 + (hasRoundBracket ? 1 : 0)) {
			label = null;
			mainIndex = 0;
		} else if (hasLabel && line.length == 4 + (hasRoundBracket ? 1 : 0) && isValidName(line[0])
				&& line[1].contentEquals(":")) {
			label = line[0];
			if (labelSet.contains(label)) {
				throw new SyntaxError("duplicate label");
			}
			labelSet.add(label);
			mainIndex = 2;
		} else {
			throw new SyntaxError("");
		}
		int curlyBracketIndex;
		int roundBracketIndex;
		if (hasRoundBracket) {
			roundBracketIndex = mainIndex + 1;
			curlyBracketIndex = mainIndex + 2;
		} else {
			roundBracketIndex = -1;
			curlyBracketIndex = mainIndex + 1;
		}
		if (!line[mainIndex].contentEquals(main)) {
			throw new SyntaxError("");
		}
		SubString roundBracketString = null;
		if (hasRoundBracket) {
			if (CharDefine.isValidOpeningBracket(line[roundBracketIndex].charAt(0)) != CharDefine.BRACKETS_TYPE_ROUND) {
				throw new SyntaxError("");
			}
			roundBracketString = StatementSpliterator.trimBrackets(line[roundBracketIndex]);
		}
		if (CharDefine.isValidOpeningBracket(line[curlyBracketIndex].charAt(0)) != CharDefine.BRACKETS_TYPE_CURLY) {
			throw new SyntaxError("");
		}
		SubString codeBlockString = StatementSpliterator.trimBrackets(line[curlyBracketIndex]);
		Statement codeBlock = BlockStatement.toRunnable(codeBlockString, supportBreak, supportContinue, true, true,
				labelSet);
		if (label != null) {
			labelSet.remove(label);
		}
		return new CommonBlock(label, roundBracketString, codeBlock);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	/**
	 * @return the longest strings that can be parsed as a number
	 */
	private static boolean isValidName(SubString string) {
		// int firstChar = string.charAt(0);
		// if (!CharDefine.isName(firstChar)) {
		// return false;
		// }
		// for (int stringIndex = 1; stringIndex < string.length(); stringIndex++) {
		// int ch = string.charAt(stringIndex);
		// if (CharDefine.isName(ch)) {
		// }
		// }
		return true;
	}
}