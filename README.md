AronScript
================
AronScript 腳本語言
<br/><strong>目前尚未完成</strong>

#### 簡介
* 直譯式語言
* 與C語言語法相似，而不像python一樣用縮排區分區塊
* 運算子重載 (operator overloading)

#### 動機/目的:
* 分離軟體核心邏輯及設定檔的腳本
* 在寫config相關的檔案時，只能用固定值來填寫，無法填寫運算式的痛點

#### 參考以下語言製作:
* 部分C語言的語法
* 部分python的使用習慣
* lua的metatable的概念

#### Quick start (call aronscript file through Java code)
* Java version: JavaSE-17
```java
FunctionData func = null;
try {
  func = AronScriptParser.parseToFunction(new File("/dir/", "parse_example_1.as"));
} catch (IOException e) {
  e.printStackTrace();
}

Data env = new Table();
ReturnThrowDataSet set = func.__apply__(env, null);
System.out.println("ENV->" + env.toString());
System.out.println((set.isThrow ? "ERROR->" : "RETURN->") + set.data.toString());
```

#### finish item
1. 語法解析
   * for
   * while
   * if、if-else、if-else-if
   * catcher
   * operators
   * comment
2. 基本資料類型操作定義
   * String
   * Number
   * Table
   * Boolean
   * Function
   * null
3. import其他AS檔案

#### TODO
1. 語法解析
   * 單元測試
   * AS的throw項目及例外檢查是否正確
3. document撰寫
4. 標準函式庫
   * printer (to console)
   * input (from console)
   * arguments (at startup)
   * string (static function)
   * number (static function)
   * table (static function)
5. 優化執行效率
6. aroncript shell
7. 移植到C語言
