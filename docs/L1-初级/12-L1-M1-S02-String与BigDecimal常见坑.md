# L1-M1-S02 String 与 BigDecimal 常见坑

## 一句话结论

- 字符串处理要注意不可变特性和拼接成本；金额计算必须用 `BigDecimal` 并显式指定舍入规则。

## 知识图

```mermaid
flowchart TD
    A[String不可变] --> B[线程安全]
    A --> C[频繁拼接成本高]
    D[BigDecimal] --> E[避免浮点误差]
    E --> F[setScale和RoundingMode]
```

## 核心知识点

### 1) String 不可变

- `String` 是不可变对象，修改会创建新对象。
- 频繁拼接优先 `StringBuilder`。
- 比较内容用 `equals`，不要混用 `==`。

### 2) 金额计算

- `double` 适合科学计算，不适合金额。
- `BigDecimal` 创建建议用字符串：`new BigDecimal("0.1")`。
- 除法要指定舍入模式，否则可能抛 `ArithmeticException`。

### 3) 常见坑

- `new BigDecimal(0.1)` 会引入二进制浮点误差。
- `setScale` 未指定舍入规则在某些场景不可控。

## 示例代码

- [`../../examples/l1/StringBigDecimalPitfallsDemo.java`](../../examples/l1/StringBigDecimalPitfallsDemo.java)

## 高频面试题

### Q1：为什么说 String 是不可变的？

答题骨架：
1. 底层字符数组引用不可改（语义上不可变）。
2. 修改操作都会生成新对象。
3. 不可变带来安全性和缓存优化收益。

### Q2：为什么金额用 BigDecimal 而不是 double？

答题骨架：
1. double 二进制表示会带精度误差。
2. BigDecimal 提供十进制精确计算。
3. 金额场景必须可控舍入。

## 复习检查

- [ ] 能举例说明 `new BigDecimal(0.1)` 的问题
- [ ] 能说明 StringBuilder 使用场景
- [ ] 能口述金额计算规范


## 前置知识

- 了解字符串拼接和变量定义。
- 知道浮点数 `double` 的基本使用。
- 会读简单的金额计算代码。

## 术语解释（零基础友好）

- **不可变对象**：对象创建后其内部状态不可修改，修改会产生新对象。
- **精度误差**：二进制浮点表示无法精确表达部分十进制小数。
- **舍入模式**：当结果位数受限时，指定如何进位或截断。

## 详细学习步骤（从不会到会）

1. 先用 `String` 做少量拼接，再用 `StringBuilder` 做循环拼接，感受差异。
2. 使用 `new BigDecimal(0.1)` 与 `new BigDecimal("0.1")` 对比输出。
3. 实现一个金额乘法并固定两位小数，显式设置舍入策略。
4. 总结“文本处理”和“金额计算”各自的安全写法。

## 常见错误与纠偏

- 用 `+` 在循环里拼接大量字符串，导致大量中间对象创建。
- 金额计算直接使用 `double`，结果在对账时出现分差。

## 学习动作

- 先手敲一次示例代码，确保可以独立运行。
- 用自己的话复述“定义 -> 原理 -> 场景 -> 边界”。
- 把本节关键结论写成 3 句速记卡，第二天复盘。

## 练习任务（建议动手）

1. 实现一个方法：把商品价格列表拼成逗号分隔文本。
2. 实现一个方法：计算“金额 * 税率”并保留两位小数。

## 练习参考方向

- 文本拼接建议优先 `StringBuilder`，最后一次性转 `String`。
- 金额计算建议全程 `BigDecimal`，并固定 `setScale` 与 `RoundingMode`。

## 复习检查

- [ ] 能在 90 秒内说明本节核心结论
- [ ] 能独立运行并解释示例代码输出
- [ ] 能说出至少 1 个常见错误与修正方式

## Java 示例代码（含注释，可直接运行）


**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

**预期输出（示例）：**
```text
text=java
total=0.60
```

```java
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        // StringBuilder 适合频繁拼接
        StringBuilder sb = new StringBuilder();
        sb.append("ja").append("va");

        // 金额场景用字符串构造 BigDecimal，避免浮点误差
        BigDecimal amount = new BigDecimal("10.00");
        BigDecimal ratio = new BigDecimal("0.06");
        BigDecimal total = amount.multiply(ratio).setScale(2, RoundingMode.HALF_UP);

        System.out.println("text=" + sb);
        System.out.println("total=" + total);
    }
}
```
