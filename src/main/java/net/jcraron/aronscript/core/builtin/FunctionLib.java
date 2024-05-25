package net.jcraron.aronscript.core.builtin;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.ClassData;
import net.jcraron.aronscript.core.base.FunctionData;

public class FunctionLib extends Data {
	public final static Data INSTANCE = new FunctionLib();
	public final static ClassData CLASS = new ClassData(FunctionLib.class);

	private FunctionLib() {
	}

	@Override
	public ReturnThrowDataSet __apply__(Data args, Data code) {
		return new Function(args, code).returnThis();
	}

	@Override
	public ReturnThrowDataSet __class__() {
		return FunctionLib.CLASS.returnThis();
	}

	private static class Function extends FunctionData {
		private Data defaultArgs;
		private Data code;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("function");
			sb.append('(');
			sb.append(this.defaultArgs.toString());
			sb.append(')');
			sb.append('{');
			sb.append(this.code.toString());
			sb.append(')');
			return sb.toString();
		}

		public Function(Data defaultArgs, Data code) {
			this.defaultArgs = defaultArgs;
			this.code = code;
		}

		@Override
		public ReturnThrowDataSet __apply__(Data args, Data code) {
			return this.code.__apply__(new Arguments(this.defaultArgs, args), code);
		}
	}

	private static class Arguments extends Data {
		private Data defaultArgs;
		private Data callArgs;

		Arguments(Data defaultArgs, Data callArgs) {
			this.defaultArgs = defaultArgs;
			this.callArgs = callArgs;
		}

		public ReturnThrowDataSet __index__(Data key) {
			ReturnThrowDataSet callResult = this.callArgs.__index__(key);
			if (callResult.isThrow) {
				return callResult;
			} else if (Data.isNull(callResult.data)) {
				return defaultArgs.__index__(key);
			}
			return callResult;
		}

		public ReturnThrowDataSet __assign__(Data key, Data value) {
			return callArgs.__assign__(key, value);
		}
	}
}
