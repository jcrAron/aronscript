AronScript
================
AronScript 腳本語言
<br/><strong>目前暫停開發</strong>

#### 簡介
* 直譯式語言
* 運算子重載 (operator overloading)

#### 動機/目的:
* 分離軟體核心邏輯及設定檔的腳本
* 在寫config相關的檔案時，只能用固定值來填寫，無法填寫運算式的痛點

#### 參考以下語言製作:
* 部分C語言的語法
* 部分python的使用習慣
* lua的metatable的概念

#### Quick start (call aronscript file through Java code)
* [example_1.as](example/example_1.as)
* Java version: JavaSE-17
```java
import java.io.File;
import java.io.IOException;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.FunctionData;
import net.jcraron.aronscript.core.base.Table;
import net.jcraron.aronscript.parser.AronScriptParser;

public class Main {
	public static void main(String... strings) {
		try {
			FunctionData func = AronScriptParser.parseToFunction(
					new File("C:\\Users\\jcrAron\\eclipse-workspace\\aronscript\\example", "example_1.as"));
			Data env = new Table();
			ReturnThrowDataSet set = func.__apply__(env, null);
			System.out.println("ENV->" + env.toString());
			System.out.println((set.isThrow ? "ERROR->" : "RETURN->") + set.data.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

#### TODO
- [x] 語法解析
  - [x] for
  - [x] while
  - [x] if、if-else、if-else-if
  - [x] catcher
  - [x] operators
  - [x] comment
  - [ ] 單元測試
  - [ ] AS的throw項目及例外檢查是否正確
- [x] 基本資料類型操作定義
  - [x] String
  - [x] Number
  - [x] Table
  - [x] Boolean
  - [x] Function
  - [x] null
- [x] import其他AS檔案
- [ ] document撰寫
- [ ] 標準函式庫
  - [x] printer (to console)
  - [ ] input (from console)
  - [ ] arguments (at startup)
  - [ ] string (static function)
  - [ ] number (static function)
  - [ ] table (static function)
- [ ] 優化執行效率
- [ ] aroncript shell
- [ ] 移植到C語言
