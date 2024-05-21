package net.jcraron.aronscript.core;

public final class ReturnThrowDataSet {
	public final boolean isThrow;
	public final Data data;
	public final static ReturnThrowDataSet RETURN_NULL = ReturnThrowDataSet.returnData(Data.NULL);

	protected ReturnThrowDataSet(boolean isThrow, Data data) {
		this.isThrow = isThrow;
		this.data = Data.isNull(data) ? Data.NULL : data;
	}

	public final boolean isThrow() {
		return isThrow;
	}

	public final Data getData() {
		return data;
	}

	/** @throw an exception if "isThrow" is true. */
	public void panic() {
		if (!this.isThrow) {
			return;
		}
		throw new RuntimeException(data.__string__().toString());
	}

	public static ReturnThrowDataSet returnData(Data data) {
		return new ReturnThrowDataSet(false, data);
	}

	public static ReturnThrowDataSet throwData(Data data) {
		return new ReturnThrowDataSet(true, data);
	}
}
