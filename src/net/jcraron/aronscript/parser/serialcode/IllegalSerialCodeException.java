package net.jcraron.aronscript.parser.serialcode;

public class IllegalSerialCodeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalSerialCodeException(String message) {
		super("invalid code: " + message);
	}
}
