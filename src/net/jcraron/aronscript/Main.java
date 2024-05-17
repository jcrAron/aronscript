package net.jcraron.aronscript;

import java.util.Arrays;
import java.util.LinkedList;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.parser.AronScriptParser;
import net.jcraron.aronscript.parser.script.exception.SyntaxError;
import net.jcraron.aronscript.parser.script.statements.StatementSpliterator;
import net.jcraron.aronscript.util.SubString;

public class Main {
	public static void main(String... strings) throws SyntaxError {
		LinkedList<FunctionData> keepObject = new LinkedList<>();
//		int test_count = 1_000_000;
		int test_count = 1;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < test_count; i++) {
			keepObject.add(test_parse2());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("parse time (ms): " + (endTime - startTime));
	}

	public static FunctionData test_parse3() throws SyntaxError {
//		a.b.c
//		a+b.c+-d
//		a+b.c*-(d+e)
//		new OperatorStatementParser().parse(StatementSpliterator.splitLine(new SubString("""
//				ta[1+2+b-c].system.out = printStream.New()
//				""")));

		return null;
	}

	public static FunctionData test_parse2() {
		FunctionData func = AronScriptParser.parseToFunction("""
				error = catcher{
					1+2+3;
					throw "throw!!!!";
				}
				if(error != null){
					return "return error: " + error;
				}
				loop_1: for(i=0;i<40;i+=1){
					if_label: if(i>=30){
						break loop_1;
					}
					if(i % 3 == 0 || i % 7 == 0){
						env[i] = true;
					}else {
						env[-i] = i % 3 == 0 || i % 7 == 0;
					}
				}
				function(a=10,b=20,c=30*60,i=i,[i]="hello"){
				}
				return (env[23] != null && env[23]) || !env[-23] ;
				""");

		Data env = new Table();
		ReturnThrowDataSet set = func.__apply__(env, null);
		System.out.println("ENV->" + env.toString());
		System.out.println((set.isThrow ? "ERROR->" : "RETURN->") + set.data.toString());
		return func;
	}

	public static FunctionData test_parse() {
		FunctionData func = AronScriptParser.parseToFunction("""
				while(false){
				 1234while;
				 continue;
				 4567;
				 break;
				};
				a = 1;
				a += 1234;
				["1234"].index123 + 1234.567 + z8910*a+b*c+!d;
				true;
				(a+b)*c["index".sidasds*(3154+lls)]+!d;
				ass = a+b && c*!d+~g--c = f+!d;
				a(b){cdefg}+
				a.c{hello}.v(b);
				a.b.c **= ss += a+c*b*b2*b3+d;
				a.b.c **= ss += a;
				hhh: if(false){
					inIF;
				}else if(false){
					inElseIF_1;
					if(false){
					}
				}else if(true){
					inElseIF_2;
					if(true){
					 123444;
					 break hhh;
					 123444;
					}
					trtr;
					throw ddd;
				}else{
					inElse;
				}
				out_if_1;
				""");
		ReturnThrowDataSet set = func.__apply__(new Table(), null);
		if (set.isThrow) {
			System.out.println(set.data);
		}
		return func;
	}

	public static void test_splitAllLines() {
		try {
			LinkedList<SubString[]> list = StatementSpliterator.splitAllLines(new SubString("""
					"12345\\"    6789"
					if(123456){
					 9981
					 } {das} + {123    }
					for(){
					 gg12345
					 "12345    6789"
					 } + 1;
					 break break;
					a+
					-
					*
					4;
					b+-*5;
					v+-*6
					"""));
			list.stream().map(Arrays::toString).forEach(System.out::println);
		} catch (SyntaxError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
