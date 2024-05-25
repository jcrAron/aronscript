package net.jcraron.aronscript.core.builtin;

import java.util.HashMap;
import java.util.Map;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.base.BooleanData;
import net.jcraron.aronscript.util.SubString;

public class BuiltInRegistry {
	private final static String CONST_NAME_IMPORT = "import";
	private final static String CONST_NAME_TABLE = "table";
	private final static String CONST_NAME_FUNCTION = "function";
	private final static String CONST_NAME_CATCHER = "catcher";
	private final static String CONST_NAME_NULL = "null";
	private final static String CONST_NAME_TRUE = "true";
	private final static String CONST_NAME_FALSE = "false";
	private final static String CONST_NAME_STRING = "string";
	private final static String CONST_NAME_NUMBER = "number";

	private static Map<SubString, Data> builtInData;
	static {
		builtInData = new HashMap<>();
		register(CONST_NAME_IMPORT, Import.INSTANCE);
		register(CONST_NAME_TABLE, TableLib.INSTANCE);
		register(CONST_NAME_FUNCTION, FunctionLib.INSTANCE);
		register(CONST_NAME_CATCHER, CatcherBlock.INSTANCE);
		register(CONST_NAME_NULL, Data.NULL);
		register(CONST_NAME_TRUE, BooleanData.TRUE);
		register(CONST_NAME_FALSE, BooleanData.FALSE);
		register(CONST_NAME_STRING, StringLib.INSTANCE);
		register(CONST_NAME_NUMBER, NumberLib.INSTANCE);
	}

	private static void register(String key, Data data) {
		builtInData.put(new SubString(key), data);
	}

	public static Data getBuiltInData(SubString name) {
		return builtInData.get(name);
	}
}
