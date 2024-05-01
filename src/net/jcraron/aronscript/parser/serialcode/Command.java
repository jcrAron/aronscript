package net.jcraron.aronscript.parser.serialcode;

import java.util.Arrays;
import java.util.Map;

import net.jcraron.aronscript.core.Operator;

public enum Command {
	UNKNOWN_COMMAND  (""              ,(byte)-1                             ),
	FILL_COMMAND     (""              ,(byte)0                              ),
	HASH             ("HASH"          ,(byte)1     ,Operator.HASH           ),
	APPLY            ("APPLY"         ,(byte)2     ,Operator.APPLY          ),
	STRING           ("STRING"        ,(byte)3     ,Operator.STRING         ),
	INDEX            ("INDEX"         ,(byte)4     ,Operator.INDEX          ),
	ASSIGN           ("ASSIGN"        ,(byte)5     ,Operator.ASSIGN         ),
	EQUAL            ("EQUAL"         ,(byte)6     ,Operator.EQUAL          ),
	NOT_EQUAL        ("NOT_EQUAL"     ,(byte)7     ,Operator.NOT_EQUAL      ),
	GREATER_THAN     ("GREATER_THAN"  ,(byte)8     ,Operator.GREATER_THAN   ),
	LESS_THAN        ("LESS_THAN"     ,(byte)9     ,Operator.LESS_THAN      ),
	GREATER_EQUAL    ("GREATER_EQUAL" ,(byte)10    ,Operator.GREATER_EQUAL  ),
	LESS_EQUAL       ("LESS_EQUAL"    ,(byte)11    ,Operator.LESS_EQUAL     ),
	LOGICAL_NOT      ("LOGICAL_NOT"   ,(byte)12    ,Operator.LOGICAL_NOT    ),
	UNARY_PLUS       ("UNARY_PLUS"    ,(byte)13    ,Operator.UNARY_PLUS     ),
	UNARY_MINUS      ("UNARY_MINUS"   ,(byte)14    ,Operator.UNARY_MINUS    ),
	ADD              ("ADD"           ,(byte)15    ,Operator.ADD            ),
	SUB              ("SUB"           ,(byte)16    ,Operator.SUB            ),
	MULTI            ("MULTI"         ,(byte)17    ,Operator.MULTI          ),
	DIV              ("DIV"           ,(byte)18    ,Operator.DIV            ),
	MOD              ("MOD"           ,(byte)19    ,Operator.MOD            ),
	RIGHT_SHIFT      ("RIGHT_SHIFT"   ,(byte)20    ,Operator.RIGHT_SHIFT    ),
	LEFT_SHIFT       ("LEFT_SHIFT"    ,(byte)21    ,Operator.LEFT_SHIFT     ),
	AND              ("AND"           ,(byte)22    ,Operator.AND            ),
	OR               ("OR"            ,(byte)23    ,Operator.OR             ),
	XOR              ("XOR"           ,(byte)24    ,Operator.XOR            ),
	BITWISE_NOT      ("BITWISE_NOT"   ,(byte)25    ,Operator.BITWISE_NOT    ),
	POW              ("POW"           ,(byte)26    ,Operator.POW            ),
	MOVE             ("@MOVE"         ,(byte)27    ,Constants.ARG_TYPE_VALUE                  ,Constants.ARG_TYPE_VALUE  ),
	CALL             ("@CALL"         ,(byte)28    ,Constants.ARG_TYPE_LABEL_ADDRESS          ,Constants.ARG_TYPE_VALUE  ),
	LABEL            ("@LABEL"        ,(byte)29    ,Constants.ARG_TYPE_LABEL_ADDRESS          ),
	JUMP             ("@JUMP"         ,(byte)30    ,Constants.ARG_TYPE_LABEL_ADDRESS          ),
	IF_FALSE         ("@IF_FALSE"     ,(byte)31    ,Constants.ARG_TYPE_LABEL_ADDRESS          ),
	CATCH            ("@CATCH"        ,(byte)32    ,Constants.ARG_TYPE_LABEL_ADDRESS          ),
	THROW            ("@THROW"        ,(byte)33    ,Constants.ARG_TYPE_VALUE       ),
	RETURN           ("@RETURN"       ,(byte)34    ,Constants.ARG_TYPE_VALUE       ),
	NEW_TABLE        ("@NEW_TABLE"    ,(byte)35    );
//	DEFINE_DATA      ("!DATA"         ,(byte)36    ,Constants.ARG_TYPE_VARIABLE_ADDRESS       ,Constants.ARG_TYPE_VALUE  ),
//	DEFINE_LABEL     ("!LABEL"        ,(byte)37    ,Constants.ARG_TYPE_LABEL_ADDRESS          ,Constants.ARG_TYPE_VALUE  );
	
	public static class Constants {
		public static final byte ARG_TYPE_LABEL_ADDRESS = 0;
		public static final byte ARG_TYPE_VALUE = 1;
//		public static final byte ARG_TYPE_VALUE = 2;

		private final static byte[][] MagicArgTypesCache;
		static {
			MagicArgTypesCache = new byte[3][];
			for(int i=0; i<MagicArgTypesCache.length; i++) {
				MagicArgTypesCache[i] = new byte[i+1];
				Arrays.fill(MagicArgTypesCache[i], ARG_TYPE_VALUE);
			}
		}
	}
	
	public final String keyword;
	public final byte codeNumber;
	public final int argsLength;
	public final Operator op;
	public final byte[] argTypes;
	
	Command(String keyword, byte codeNumber, byte... argTypes) {
		this.keyword = keyword;
		this.codeNumber = codeNumber;
		this.op = null;
		this.argsLength = argTypes.length; 
		this.argTypes = argTypes;
	}
	
	Command(String keyword, byte codeNumber, Operator op) {
		this.keyword = keyword;
		this.codeNumber = codeNumber;
		this.op = op;
		this.argsLength = op.getParams().length;
		this.argTypes = Constants.MagicArgTypesCache[op.getParams().length - 1];
	}
	
	public boolean isMagicCommand() {
		return op != null;
	}

	/*----------------------------------------------------------------------*/
	private static Map<Byte,Command> idMap;
	static {
		for(Command item : Command.values()) {
			idMap.put(item.codeNumber, item);
		}
	}
	public static Command getItemById(byte codeNumber) {
		return idMap.getOrDefault(codeNumber, UNKNOWN_COMMAND);
	}
	/*----------------------------------------------------------------------*/
	private static Map<String,Command> nameMap;
	static {
		for(Command item : Command.values()) {
			nameMap.put(item.keyword, item);
		}
	}
	public static Command getItemByName(String keyword) {
		return nameMap.getOrDefault(keyword, UNKNOWN_COMMAND);
	}
}