# 蒙提霍爾驗證遊戲（Monty Hall Problem）

用 Java 寫的 **Windows console 互動小遊戲**，以實際遊玩與大量模擬驗證「蒙提霍爾問題」中**換門**策略的勝率約為 2/3。

- 類型：命令列（CLI）互動程式／機率模擬
- 語言：Java 25（純 JDK，無第三方依賴）
- 建置：Maven，可另外打包成 Windows 可執行檔（`.exe`）

## 什麼是蒙提霍爾問題

節目中有三扇門，其中一扇門後是汽車，另外兩扇門後是山羊：

1. 參賽者先選一扇門。
2. 知道答案的主持人打開另一扇門，門後一定是山羊。
3. 主持人問參賽者要不要換到剩下那扇門。

直覺上換不換的勝率都是 1/2，但實際上**換門的勝率是 2/3**：只要初選選到山羊（機率 2/3），換門就一定會中汽車。

## 本程式的模擬方式

每一局都會把三個位置（2 隻羊、1 輛車）重新洗牌。程式採用「一律換門」的觀點來判定勝負：

> **初選選到「羊」即算勝利**，因為換門後必定得到汽車。

因此不論手動或自動模式，長期的平均命中率都應該接近 **66.67%**。

## 環境需求

- Windows（程式透過 `cmd /c cls` 清除畫面，需在 CMD 或 Windows Terminal 中執行）
- JDK 25 以上
- Maven 3.x

## 建置與執行

### 方式一：直接執行

在 IDE 中執行 `idv.module.App` 的 `main` 方法。

> 注意：IDE 內建 console 無法清除畫面，建議改在 CMD 中執行打包後的 jar 或 exe。

### 方式二：打包後執行

```bash
mvn clean package
```

會在 `target/` 產生：

| 檔案 | 說明 | 執行方式 |
| --- | --- | --- |
| `monty-hall-problem.jar` | 可執行 jar | `java -jar target/monty-hall-problem.jar` |
| `monty-hall-problem.exe` | 由 launch4j 包裝的 Windows 執行檔 | 直接執行 `target/monty-hall-problem.exe`（電腦需安裝 Java 25） |

## 操作說明

啟動後依照 console 提示輸入：

| 輸入 | 作用 |
| --- | --- |
| `1` | 自動模式 |
| `2` | 手動模式 |
| `0` | 離開目前模式，回到模式選單 |
| `exit` | 任何時候都可以直接結束程式 |

### 自動模式

輸入模擬次數（**200 ~ 10,000,000**），程式會一次跑完並顯示平均命中率，例如：

```
Mode: automatic
Frequency: 1000000
Average hit rate: 66.68%
```

### 手動模式

每局輸入要選的位置：`1` 左、`2` 中、`3` 右。程式會揭曉三個位置的內容與輸贏，並累計目前局數與平均命中率：

```
	√
╒═══════════════╤═══════════════╤═══════════════╕
|	羊	|	車	|	羊	|
╘═══════════════╧═══════════════╧═══════════════╛

You win!

Sequence number of play： 1
Average hit rate： 100.00%
```

玩得越多局，命中率會越接近 2/3。

## 專案結構

```
src/main/java/idv/module/
├── App.java                 # 程式進入點，建立輸入串流並啟動流程
├── ui/ProblemFlow.java      # 模式選單、自動／手動模式流程與結果輸出
├── model/ProblemData.java   # 每局洗牌後的三個位置（2 羊 1 車）
└── utils/InputUtils.java    # 讀取輸入，處理 exit 結束程式
src/main/resources/icon/icon.ico  # exe 圖示
```
