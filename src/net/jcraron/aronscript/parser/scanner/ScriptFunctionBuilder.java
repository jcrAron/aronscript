package net.jcraron.aronscript.parser.scanner;

import java.util.LinkedList;
import java.util.List;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.FunctionData;

public final class ScriptFunctionBuilder {
	private ScriptFunction function = new ScriptFunction();

	private void checkReady() {
		if (function == null) {
			throw new RuntimeException("function has been built");
		}
	}

	public void append(String operate, String... data) {
		checkReady();
		function.operate.add(data);
	}

	public FunctionData build() {
		checkReady();
		function = null;
		return function;
	}

	private class ScriptFunction extends FunctionData {
		List<String[]> binaryList;

		ScriptFunction() {
			this.binaryList = new LinkedList<>();
		}

		private void run(String[] args) {
		}

		@Override
		public ReturnThrowDataSet __apply__(Data args) {
			for (String[] bin : binaryList) {
				ReturnThrowDataSet set = BinaryExecutor.execute(args, bin);
				if (set.isThrow) {
					return set;
				}
			}
			return null;
		}
	}
}