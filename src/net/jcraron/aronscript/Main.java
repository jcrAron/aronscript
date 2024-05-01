package net.jcraron.aronscript;

import net.jcraron.aronscript.core.Operator;
import net.jcraron.aronscript.core.base.NumberData;
import net.jcraron.aronscript.core.base.StringData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.parser.serialcode.BinaryCodesExecutor;

public class Main {
	public static void main(String... strings) {
		NumberData num1 = NumberData.valueOf(0.1);
		NumberData num2 = NumberData.valueOf(0.2);
		NumberData num3 = NumberData.valueOf(0.3);
		System.out.println(num1.operate(Operator.ADD, num2).getData().operate(Operator.EQUAL, num3).data);
		Table table1 = new Table();
		table1.operate(Operator.ASSIGN, StringData.valueOf("key1"), StringData.valueOf("value1"));
		table1.operate(Operator.ASSIGN, StringData.valueOf("key2"), StringData.valueOf("value2"));
		table1.operate(Operator.ASSIGN, StringData.valueOf("key3"), StringData.valueOf("value3"));
		System.out.println(table1);
		System.out.println(table1.operate(Operator.INDEX, StringData.valueOf("key2")).data);
		System.out.println(StringData.valueOf("string").operate(Operator.ADD, StringData.valueOf("...")).data);
		System.out.println(StringData.valueOf("abc").operate(Operator.MULTI, NumberData.valueOf(4)).data);
	}
}
