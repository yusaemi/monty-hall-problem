# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 專案概述

以 console 互動方式驗證蒙提霍爾問題（Monty Hall problem）的小型 Java 程式。純 JDK、無任何第三方依賴，也沒有測試。

## 常用指令

- 編譯：`mvn clean compile`
- 打包 jar 與 exe：`mvn clean package` → 產出 `target/monty-hall-problem.jar` 與 `target/monty-hall-problem.exe`（由 `launch4j-maven-plugin` 在 `package` phase 產生，console header，icon 來自 `src/main/resources/icon/icon.ico`）
- 執行：`java -jar target/monty-hall-problem.jar`，或在 IDE 直接執行 `idv.module.App` 的 `main`

## 執行環境限制

- 需要 **Java 25**（`pom.xml` 的 `java.version`）。程式碼使用 Java 25 才定案的功能：`App` 的 instance main method（`void main()`，非 `public static`）以及 `java.lang.IO`（`IO.println`）。輸出統一用 `IO.println`；不換行的提示仍用 `System.out.print`。
- **僅支援 Windows**：`ProblemFlow` 每次刷新畫面都呼叫 `new ProcessBuilder("cmd", "/c", "cls")`，需在真正的 CMD console 下執行（IDE 內建 console 不會清畫面）。

## 架構

流程集中在 `idv.module.ui.ProblemFlow`（static 方法 + static `ProblemData data` 狀態）：

1. `App.main` 建立 `BufferedReader(System.in)` 交給 `ProblemFlow.start`。
2. 外層迴圈選模式：`1` 自動、`2` 手動；內層迴圈中輸入 `0` 回到模式選單。
3. 所有輸入都經過 `InputUtils.getInput`：任何時候輸入 `exit` 都會直接 `System.exit(0)`。
4. 每輪開始前 `data = new ProblemData()` 重新洗牌。

### 機率模型（容易誤解的地方）

`ProblemData.pools` 是洗牌後的 3 個元素：2 個 `"pickOn"`、1 個 `"skunk"`。`"pickOn"` 在畫面上顯示為「羊」、`"skunk"` 顯示為「車」，而**抽中 `"pickOn"`（羊）算勝利**。也就是說，程式模擬的是「換門策略」：初選選到羊 → 換門必中車。因此兩種模式的預期勝率都約為 66.67%。修改勝負判定或顯示文字時務必維持這個語意。

- 自動模式（`autoStart`）：次數限制為 200 ~ 10,000,000，每次從 pools 隨機取一個 index 判定，計算平均命中率。
- 手動模式（`manuallyStart`）：使用者選 1~3，揭示三個位置並累計勝率（計數器以 `AtomicInteger` 跨輪保存）。
