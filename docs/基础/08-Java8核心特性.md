# 08 Java 8 核心特性

## 本章目标

- 掌握 Java 8 在现代代码中的核心用法。
- 理解 Lambda、Stream、Optional、java.time 的适用边界。
- 避免把新特性用成“可读性灾难”。

## 一、Lambda 表达式

Lambda 让“行为作为参数”更简洁，典型用于集合处理和回调逻辑。

注意：

- 复杂逻辑不要硬塞 Lambda，必要时抽成具名方法。
- 可读性优先于“写得短”。

## 二、Stream 流式处理

常见流程：`filter -> map -> sorted -> collect`

优势：

- 声明式表达，减少样板循环代码。
- 易于组合数据转换逻辑。

风险：

- 过度链式调用导致可读性下降。
- 并行流不是性能银弹，需要结合场景评估。

## 三、Optional

`Optional` 用于表达“可能为空”的语义，减少嵌套判空。

- 适合返回值语义表达。
- 不建议作为实体类字段长期持有。

## 四、java.time

- `LocalDate`：日期。
- `LocalDateTime`：日期+时间。
- `Duration`：时长。

相比旧日期 API，更清晰且线程安全。

## 五、练习任务

1. 把一段 for 循环筛选逻辑改写为 Stream。
2. 用 Optional 改造三层判空代码。
3. 用 java.time 计算“订单创建后 30 分钟是否超时”。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = List.of("alice", "", "bob", "carol");

        // Stream: 过滤空字符串 + 转大写
        List<String> cleaned = names.stream()
                .filter(name -> !name.isBlank())
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // Optional: 明确“可能不存在”的默认值
        String displayName = Optional.<String>ofNullable(null).orElse("GUEST");

        // java.time: 计算时间间隔
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(45);
        boolean timeout = Duration.between(createdAt, LocalDateTime.now()).toMinutes() > 30;

        System.out.println("cleaned=" + cleaned);
        System.out.println("displayName=" + displayName);
        System.out.println("timeout=" + timeout);
    }
}
```

**预期输出示例：**

```text
cleaned=[ALICE, BOB, CAROL]
displayName=GUEST
timeout=true
```

## 返回

- [`README.md`](./README.md)
