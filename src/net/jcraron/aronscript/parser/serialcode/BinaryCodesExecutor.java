package net.jcraron.aronscript.parser.serialcode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.core.base.NumberData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.core.base.Table;

/** for optimization */
public class BinaryCodesExecutor {
	private BunaryCodeRuntime runtime;
	private SerialCodes codes;

	public BinaryCodesExecutor(SerialCodes codes) {
		this(new BunaryCodeRuntime(), codes);
	}

	public BinaryCodesExecutor(BunaryCodeRuntime runtime, SerialCodes codes) {
		this.runtime = runtime;
		this.codes = codes;
	}

	/** @return true if the execution is successful */
	public ReturnThrowDataSet executeAll(Data env) {
		runtime.init(env);
		// runtime.setOptimization(codes.getOptimization());
		while (runtime.getPC() < codes.getBinaryCodes().length) {
			reviewLabels();
		}
		runtime.reset(env);
		while (runtime.getPC() < codes.getBinaryCodes().length) {
			executeOneLine();
		}
		ReturnThrowDataSet result = runtime.finish();
		if (result == null) {
			throw new IllegalSerialCodeException("program incorrectly closed");
		}
		return result;
	}

	private byte takeCode() {
		byte c = codes.getBinaryCodes()[runtime.getPC()];
		runtime.countPC(1);
		return c;
	}

	/*---------------------------------------------------------------------------------------------*/

	private void reviewLabels() {
		Command cmd = Command.getItemById(takeCode());
		if (cmd == Command.LABEL) {
			runtime.label(takeLabelIndex());
		} else {
			for (int i = 0; i < cmd.argsLength; i++) {
				switch (cmd.argTypes[i]) {
				case Command.Constants.ARG_TYPE_VALUE:
					takeValue();
				case Command.Constants.ARG_TYPE_LABEL_ADDRESS:
					takeLabelIndex();
				}
			}
		}
	}

	private void executeOneLine() {
		Command cmd = Command.getItemById(takeCode());
		runtime.countPC(cmd.argsLength);
		if (cmd.isMagicCommand()) {
			Data self = takeValue();
			Data[] values = null;
			if (cmd.argsLength > 0) {
				values = new Data[cmd.argsLength];
				for (int i = 0; i < values.length; i++) {
					values[i] = takeValue();
				}
			}
			ReturnThrowDataSet set = self.operate(cmd.op, values);
			if (set.isThrow) {
				runtime.flow_throw(set.data);
			} else {
				runtime.setOutput(set.data);
			}
		} else {
			switch (cmd) {
			case CALL:
				runtime.flow_call(takeLabelIndex(), takeValue());
				break;
			case CATCH:
				runtime.flow_catch(takeLabelIndex());
				break;
			case IF_FALSE:
				runtime.flow_ifFalse(takeLabelIndex());
				break;
			case JUMP:
				runtime.flow_jump(takeLabelIndex());
				break;
			case LABEL:
//				pass
				break;
			case MOVE:
				runtime.setCache((int) ((NumberData) takeValue()).value, takeValue());
				break;
			case RETURN:
				runtime.flow_return(takeValue());
				break;
			case THROW:
				runtime.flow_throw(takeValue());
				break;
			case NEW_TABLE:
				runtime.setOutput(new Table());
				break;
			case FILL_COMMAND:
				// pass
				break;
			case UNKNOWN_COMMAND:
				throw new IllegalSerialCodeException("unknown command");
			default:
				throw new IllegalSerialCodeException("compiler missing code");
			}
		}
	}
	/*----------------------------------------------------------------------------*/

	private int takeLabelIndex() {
		return Byte.toUnsignedInt(takeCode());
	}

	/*----------------------------------------------------------------------------*/
	private final static byte IS_TRUE = (byte) 0; // yes, it's zero.
	private final static byte IS_NUMBER = (byte) 0b1000_0000;
	private final static byte IS_CACHE_VALUE = (byte) 0b0100_0000;
	private final static byte IS_EXTEND = (byte) 0b0010_0000;
	private final static byte TYPE_MASK = (byte) 0b0001_1000;
	private final static byte ADDRESS_MASK = (byte) 0b0011_1111;
	private final static byte LENGTH_MASK = (byte) 0b0000_0111;

	private final static byte INDEX_NULL = 0;
	private final static byte INDEX_TRUE = 1;
	private final static byte INDEX_FALSE = 2;
	private final static byte INDEX_OUT = 3;
	private final static byte INDEX_ENV = 4;

	private final static byte CODE_START = (byte) 0b1000_0000;
	private final static byte CODE_NULL = CODE_START | INDEX_NULL;
	private final static byte CODE_TRUE = CODE_START | INDEX_TRUE;
	private final static byte CODE_FALSE = CODE_START | INDEX_FALSE;
	private final static byte CODE_OUT = CODE_START | INDEX_OUT;
	private final static byte CODE_ENV = CODE_START | INDEX_ENV;

	private final static byte TYPE_STRING = (byte) 0b0000_0000;
	private final static byte TYPE_NUMBER = (byte) 0b0000_1000;
	private final static byte TYPE_ADDRESS = (byte) 0b0001_0000;
	private final static byte TYPE_OBJECT = (byte) 0b0001_1000;

	private Data takeValue() {
		byte num = takeCode();
		if ((num & IS_NUMBER) == IS_TRUE) {
			return NumberData.valueOf(num);
		} else if (num == CODE_NULL) {
			return null;
		} else if (num == CODE_TRUE) {
			return BooleanData.TRUE;
		} else if (num == CODE_FALSE) {
			return BooleanData.FALSE;
		} else if (num == CODE_OUT) {
			return runtime.getOutput();
		} else if (num == CODE_ENV) {
			return runtime.getEnv();
		} else if ((num & IS_CACHE_VALUE) == IS_TRUE) {
			return runtime.getCache(num & ADDRESS_MASK);
		} else {
			byte type = (byte) (num & TYPE_MASK);
			long lenght = (byte) (num & LENGTH_MASK) + 1;
			if ((num & IS_EXTEND) == IS_TRUE) {
				lenght = parseValue_length(lenght);
			}
			switch (type) {
			case TYPE_STRING:
				return StringData.valueOf(parseValue_string(lenght));
			case TYPE_NUMBER:
				return parseValue_number(lenght);
			case TYPE_ADDRESS:
				return runtime.getCache(parseValue_cache(lenght));
			case TYPE_OBJECT:
				return parseValue_table(lenght);
			}
		}
		return null;
	}

	/** @param length between 1 and 8 */
	private long parseValue_length(long length) {
		assert length >= 1 && length <= 8;
		long extendLength = 0;
		for (int count = 0; count < length; count++) {
			extendLength |= takeCode() << (count * Byte.SIZE);
		}

		return extendLength;
	}

	private String parseValue_string(long length) {
		InputStream input = new InputStream() {
			long count = 0;

			@Override
			public int available() {
				return Long.compareUnsigned(length, count);
			}

			@Override
			public int read() throws IOException {
				if (available() <= 0) {
					throw new IllegalSerialCodeException("over read on parseValue");
				}
				count++;
				return takeCode();
			}
		};
		String result = "";
		try {
			result = new String(input.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Data parseValue_number(long length) {
		String numString = parseValue_string(length);
		return NumberData.valueOf(Double.parseDouble(numString));
	}

	private int parseValue_cache(long length) {
		length = Long.min(length, 4);
		int num = 0;
		for (int i = 0; i < length; i++) {
			num |= takeCode() << (i * Byte.SIZE);
		}
		return num;
	}

	private Data parseValue_table(long length) {
		return null;
	}

}
