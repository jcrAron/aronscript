package net.jcraron.aronscript.util;

public class SubString implements CharSequence {
	public final static SubString EMPTY = new SubString(String.valueOf(""));
	private String string;
	private int offset;
	private int length;

	private SubString(String string, int start, int length) {
		this.string = string;
		this.offset = start;
		this.length = length;
	}

	public SubString(String string) {
		this(string, 0, string.length());
	}

	public SubString substring(int start, int end) {
		if (start > end) {
			throw new ArrayIndexOutOfBoundsException("start= " + start + ", end=" + end);
		}
		int length = end - start;
		if (start == this.offset && length == this.length) {
			return this;
		}
		return new SubString(this.string, this.offset + start, length);
	}

	public SubString substring(int start) {
		if (start == 0) {
			return this;
		}
		return this.substring(start, this.length);
	}

	@Override
	public char charAt(int index) {
		if (index < 0 || index >= this.length) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return this.string.charAt(this.offset + index);
	}

	public boolean startsWith(String string) {
		return this.string.startsWith(string, this.offset);
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public String toString() {
		return this.string.substring(this.offset, this.offset + this.length);
	}

	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}

	public int indexOf(String string) {
		return this.string.indexOf(string, this.offset);
	}

	public SubString strip() {
		return this.stripLeading().stripTrailing();
	}

	public SubString stripTrailing() {
		for (int i = this.length - 1; i >= 0; i--) {
			int ch = this.charAt(i);
			if (ch != ' ' && ch != '\t' && !Character.isWhitespace(ch)) {
				return this.substring(0, i + 1);
			}
		}
		return this;
	}

	public SubString stripLeading() {
		for (int i = 0; i < this.length(); i++) {
			int ch = this.charAt(i);
			if (ch != ' ' && ch != '\t' && !Character.isWhitespace(ch)) {
				return this.substring(i, this.length());
			}
		}
		return this;
	}

	public boolean contentEquals(CharSequence seq) {
		if (this.length != seq.length()) {
			return false;
		}
		for (int i = 0; i < this.length; i++) {
			if (this.charAt(i) != seq.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.substring(start, end);
	}

	public static boolean contentEquals(SubString substring, CharSequence seq) {
		if (substring == seq) {
			return true;
		} else if (substring == null || seq == null) {
			return false;
		}
		return substring.contentEquals(seq);
	}

	@Override
	public int hashCode() {
		int hash = 0;
        for (int i = 0; i < length; i++) {
        	hash = 31 * hash + this.charAt(i);
        }
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CharSequence seq))
			return false;
		return this.contentEquals(seq);
	}

}