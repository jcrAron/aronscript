package net.jcraron.aronscript.parser.scanner;

import java.util.Arrays;

/** only support Unicode code point */
public class CharUtil {

	public final static int[] BRACKETS_TYPE_ROUND = new int[] { '(', ')' };
	public final static int[] BRACKETS_TYPE_SQUARE = new int[] { '[', ']' };
	public final static int[] BRACKETS_TYPE_CURLY = new int[] { '{', '}' };
	public final static int[] BRACKETS_TYPE_QUOTATION = new int[] { '"', '"' };

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
		return isValidSymbol(ch);
	}

	// AronScript util ------------------------------------------------------

	/** @return true if ch is defined by AronScript. */
	public static boolean isValidChar(int ch) {
		return Character.isDefined(ch) && !isFullwidth(ch);
	}

	public static boolean isName(int ch) {
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
	public static int[] isValidBracket(int ch) {
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

	/** @return true if ch is a symbol defined by AronScript */
	public static boolean isValidSymbol(int ch) {
		return (inRange(ch, 0x21, 0x2F) || inRange(ch, 0x3A, 0x40) || inRange(ch, 0x5B, 0x5E) || inRange(ch, 0x5B, 0x5E)
				|| ch == 0x60 || inRange(ch, 0x7B, 0x7E));
	}

	/** @return (i >= min && i <= max) */
	private static boolean inRange(int i, int min, int max) {
		return i >= min && i <= max;
	}

}
