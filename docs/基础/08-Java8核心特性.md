# 08 Java 8 核心特性

## 本章目标

- 掌握 Java 8 最核心且高频的语言能力。
- 了解 Lambda、Stream、Optional、java.time 的适用边界。
- 避免“链式写法很炫，但团队难维护”的问题。

## 前置知识

- 会使用集合和循环。
- 理解基础函数式接口概念。

## 核心概念

1. Lambda：把行为作为参数。
2. Stream：声明式数据处理链。
3. Optional：明确可空语义。
4. java.time：现代时间 API。

## 原理展开

- Stream 擅长表达“过滤、映射、聚合”逻辑。
- Optional 解决的是可读性和语义问题，不是性能优化。
- java.time 相比旧 API 语义更清晰且线程安全。

## 典型场景

- 用户列表筛选与格式化。
- 空值默认策略处理。
- 时间窗口与超时判断。

## 常见误区

1. 把所有逻辑都写成超长 Stream 链。
2. Optional 嵌套过深导致可读性变差。
3. 仍在新项目中使用老旧日期 API。

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

        // Stream: 过滤空值并做统一转换
        List<String> cleaned = names.stream()
                .filter(name -> !name.isBlank())
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // Optional: 用默认值承接空语义
        String displayName = Optional.<String>ofNullable(null).orElse("GUEST");

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

## 练习与自测

1. 把 for 循环筛选逻辑改写为 Stream。
2. 用 Optional 改造一段三层判空代码。
3. 自测：能否解释“为什么 Optional 不适合做实体字段”。

## 本章小结

- Java 8 不是为了写得更短，而是为了表达更清晰。
- 新特性要服务业务可读性和维护性。

## 下一章

- [`09-Java9到25关键特性.md`](./09-Java9到25关键特性.md)
