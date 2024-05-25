package net.jcraron.aronscript.util;

import java.util.Arrays;

public class SubArray<T> {
	private T[] array;
	private int offset;
	private int length;

	private SubArray(T[] array, int offset, int length) {
		this.array = array;
		this.offset = offset;
		this.length = length;
	}

	public SubArray(T[] array) {
		this(array, 0, array.length);
	}

	public T[] toArray() {
		if (this.offset == 0 && this.length == array.length) {
			return array;
		}
		return Arrays.copyOfRange(array, offset, offset + length);
	}

	public SubArray<T> subarray(int start) {
		if (start == 0) {
			return this;
		}
		return this.subarray(start, this.length);
	}

	public SubArray<T> subarray(int start, int end) {
		if (start > end) {
			throw new ArrayIndexOutOfBoundsException("start= " + start + ", end=" + end);
		}
		int length = end - start;
		if (start == this.offset && length == this.length) {
			return this;
		}
		return new SubArray<>(this.array, this.offset + start, length);
	}

	public int length() {
		return length;
	}

	public T index(int i) {
		return this.array[offset + i];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int index = 0; index < this.length; index++) {
			sb.append(this.index(index));
			if (index < this.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(']');
		return sb.toString();
	}
}
