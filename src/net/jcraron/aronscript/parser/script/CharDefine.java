package net.jcraron.aronscript.parser.script;

import java.util.Arrays;

/** only support Unicode code point */
public class CharDefine {

	public final static int[] BRACKETS_TYPE_ROUND = new int[] { '(', ')' };
	public final static int[] BRACKETS_TYPE_SQUARE = new int[] { '[', ']' };
	public final static int[] BRACKETS_TYPE_CURLY = new int[] { '{', '}' };
	public final static int[] BRACKETS_TYPE_QUOTATION = new int[] { '"', '"' };
	public final static int CHAR_ESCAPE = '\\';
	public final static int CHAR_END_STATEMENT = ';';

	/** be sorted */
	public final static int[] VALID_SPACES;

	static {
		int[] valid_spaces = new int[] { ' ', '\t', '\n', '\r', '\0' };
		Arrays.sort(valid_spaces);
		VALID_SPACES = valid_spaces;
	}

	// common util ----------------------------------------------------------

	public static boolean isFullwidth(int ch) {
		return ch >= 0xFF00 && ch <= 0xFFEF;
	}

	/** @return true if ch is a space defined by Unicode. */
	public static boolean isSpace(int ch) {
		return Character.isWhitespace(ch);
	}

	/** @return true if ch is any symbol */
	public static boolean isSymbol(int ch) {
		return inRange(ch, 0x21, 0x2F) || inRange(ch, 0x3A, 0x40) || inRange(ch, 0x5B, 0x5E) || inRange(ch, 0x5B, 0x5E)
				|| ch == 0x60 || inRange(ch, 0x7B, 0x7E) || ch == CHAR_END_STATEMENT;
	}

	// AronScript util ------------------------------------------------------

	/** @return true if ch is a char defined by AronScript. */
	public static boolean isValidChar(int ch) {
		return Character.isDefined(ch) && !isFullwidth(ch);
	}

	public static boolean isNumber(int ch) {
		return ch >= '0' && ch <= '9';
	}

	public static boolean isText(int ch) {
		return !isSpace(ch) && !isSymbol(ch);
	}

	/**
	 * @return true if ch is a space defined by AronScript. including tab,carriage
	 *         return etc
	 */
	public static boolean isValidSpace(int ch) {
		return Arrays.binarySearch(VALID_SPACES, ch) >= 0;
	}

	/**
	 * @return an int array if ch is a bracket defined by AronScript. otherwise,
	 *         return null.</br>
	 *         The zeroth index of the array is the opening bracket.</br>
	 *         The first index of the array is the closing bracket.
	 */
	public static int[] isValidOpeningBracket(int ch) {
		if (ch == BRACKETS_TYPE_ROUND[0]) {
			return BRACKETS_TYPE_ROUND;
		}
		if (ch == BRACKETS_TYPE_SQUARE[0]) {
			return BRACKETS_TYPE_SQUARE;
		}
		if (ch == BRACKETS_TYPE_CURLY[0]) {
			return BRACKETS_TYPE_CURLY;
		}
		if (ch == BRACKETS_TYPE_QUOTATION[0]) {
			return BRACKETS_TYPE_QUOTATION;
		}
		return null;
	}

	public static boolean isMatchBracket(int opening, int closing) {
		if (opening == BRACKETS_TYPE_ROUND[0] && closing == BRACKETS_TYPE_ROUND[1]) {
			return true;
		}
		if (opening == BRACKETS_TYPE_SQUARE[0] && closing == BRACKETS_TYPE_SQUARE[1]) {
			return true;
		}
		if (opening == BRACKETS_TYPE_CURLY[0] && closing == BRACKETS_TYPE_CURLY[1]) {
			return true;
		}
		if (opening == BRACKETS_TYPE_QUOTATION[0] && closing == BRACKETS_TYPE_QUOTATION[1]) {
			return true;
		}
		return false;
	}

	/** @return true if ch is a symbol defined by AronScript */
	public static boolean isValidOperatorSymbol(int ch) {
		return Symbol.SYMBOL_CHARS.contains(ch);
	}

	/** @return (i >= min && i <= max) */
	private static boolean inRange(int i, int min, int max) {
		return i >= min && i <= max;
	}

}
