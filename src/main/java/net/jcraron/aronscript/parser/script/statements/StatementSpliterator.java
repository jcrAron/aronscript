package net.jcraron.aronscript.parser.script.statements;

import java.util.Deque;
import java.util.LinkedList;

import net.jcraron.aronscript.parser.script.CharDefine;
import net.jcraron.aronscript.parser.script.Symbol;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.util.SubString;

public class StatementSpliterator {

	/**
	 * @return lines that do not include ";".
	 *         <ol>
	 *         <li>Separate all strings with ";".</li>
	 *         <li>Separate the lines without operators after curly braces.</li>
	 *         </ol>
	 */
	public static LinkedList<SubString[]> splitAllLines(SubString string) throws SyntaxError {
		LinkedList<SubString[]> lines = new LinkedList<>();
		LinkedList<SubString> oneLine = new LinkedList<>();
		boolean isCurlyBracketAtLast = false;
		boolean shouldAddLine = false;
		boolean commentMode = false;
		for (int i = 0; i < string.length(); i++) {
			int ch = string.charAt(i);
			if (commentMode) {
				if (ch == CharDefine.COMMENT_CLOSING) {
					commentMode = false;
				}
				continue;
			} else if (CharDefine.isCommentOpening(string, i)) {
				commentMode = true;
				i += CharDefine.COMMENT_OPENING.length() - 1;
				continue;
			}
			if (CharDefine.isValidSpace(ch)) {
				continue;
			}
			if (shouldAddLine || (isCurlyBracketAtLast && !CharDefine.isValidOperatorSymbol(ch))) {
				if (!oneLine.isEmpty()) {
					SubString[] line = oneLine.toArray(size -> new SubString[size]);
					lines.add(line);
					oneLine.clear();
				}
				shouldAddLine = false;
			}
			isCurlyBracketAtLast = false;
			int[] bracketType = CharDefine.isValidOpeningBracket(ch);
			if (bracketType != null) {
				SubString bracketString = bracket(string.substring(i));
				i += bracketString.length() - 1;
				oneLine.addLast(bracketString);
				if (bracketType == CharDefine.BRACKETS_TYPE_CURLY) {
					isCurlyBracketAtLast = true;
				}
			} else if (CharDefine.isSymbol(ch)) {
				SubString symbolString = symbol(string.substring(i));
				i += symbolString.length() - 1;
				if (symbolString.charAt(0) == CharDefine.CHAR_END_STATEMENT) {
					shouldAddLine = true;
				} else {
					oneLine.add(symbolString);
				}
			} else {
				SubString textString = text(string.substring(i));
				i += textString.length() - 1;
				oneLine.add(textString);
			}
		}
		if (isCurlyBracketAtLast || !oneLine.isEmpty()) {
			SubString[] line = oneLine.toArray(size -> new SubString[size]);
			lines.add(line);
		}
		return lines;
	}

	public static SubString[] splitLine(SubString string) throws SyntaxError {
		LinkedList<SubString> oneLine = new LinkedList<>();
		for (int i = 0; i < string.length(); i++) {
			int ch = string.charAt(i);
			if (CharDefine.isValidSpace(ch)) {
				continue;
			}
			int[] bracketType = CharDefine.isValidOpeningBracket(ch);
			if (bracketType != null) {
				SubString bracketString = bracket(string.substring(i));
				i += bracketString.length() - 1;
				oneLine.addLast(bracketString);
			} else if (CharDefine.isSymbol(ch)) {
				SubString symbolString = symbol(string.substring(i));
				i += symbolString.length() - 1;
				oneLine.add(symbolString);
			} else {
				SubString textString = text(string.substring(i));
				i += textString.length() - 1;
				oneLine.add(textString);
			}
		}
		return oneLine.toArray(size -> new SubString[size]);
	}

	public static SubString trimBrackets(SubString bracket) {
		if (!CharDefine.isMatchBracket(bracket.charAt(0), bracket.charAt(bracket.length() - 1))) {
			throw new RuntimeException(""); // TODO
		}
		return bracket.substring(1, bracket.length() - 1);
	}

	/** Try to find the longest symbol from the beginning of the string */
	private static SubString symbol(SubString string) {
		string = string.stripLeading();
		SubString ret = string.substring(0, 1);
		for (int i = 1; i < string.length(); i++) {
			SubString sub = string.substring(0, i + 1);
			if (Symbol.foundSymbolByString(sub)) {
				ret = sub;
			}
			if (!CharDefine.isSymbol(string.charAt(i))) {
				break;
			}
		}
		return ret;
	}

	/**
	 * Try to find the longest text from the beginning of the string
	 * 
	 * @throws SyntaxError
	 */
	private static SubString text(SubString input) throws SyntaxError {
		input = input.stripLeading();
		boolean isNumberMode = CharDefine.isNumber(input.charAt(0));
		boolean hasDot = false;
		for (int i = 0; i < input.length(); i++) {
			int ch = input.charAt(i);
			if (isNumberMode) {
				if (!(CharDefine.isNumber(ch) || (!hasDot && ch == '.'))) {
					return input.substring(0, i);
				}
				if (ch == '.') {
					if (hasDot) {
						throw new SyntaxError("Numbers cannot contain two dots");
					}
					hasDot = true;
				}
			} else if (!CharDefine.isText(ch)) {
				return input.substring(0, i);
			}
		}
		return input;
	}

	/**
	 * @param input with brackets
	 * @return with brackets
	 * @throws SyntaxError 
	 */
	private static SubString bracket(SubString input) throws SyntaxError {
		if (CharDefine.isValidOpeningBracket(input.charAt(0)) == null) {
			throw new RuntimeException("input must have opening brackets");
		}
		Deque<int[]> bracketStack = new LinkedList<>();
		SubString subString = null;
		boolean escMode = false;
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (bracketStack.peekFirst() == CharDefine.BRACKETS_TYPE_QUOTATION) {
				if (escMode) {
					escMode = false;
					continue;
				} else if (ch == CharDefine.CHAR_ESCAPE) {
					escMode = !escMode;
					continue;
				} else if (ch == '\n') {
					throw new SyntaxError("Quotes must be on the same line");
				}
			} else {
				int[] bracket = CharDefine.isValidOpeningBracket(ch);
				if (bracket != null) {
					bracketStack.push(bracket);
					continue;
				}
			}
			if (CharDefine.isMatchBracket(bracketStack.peekFirst()[0], ch)) {
				bracketStack.pop();
				if (bracketStack.isEmpty()) {
					subString = input.substring(0, i + 1);
					break;
				}
			}
		}
		if (subString == null) {
			throw new RuntimeException("bracket is unbalanced");
		}
		return subString;
	}
}
