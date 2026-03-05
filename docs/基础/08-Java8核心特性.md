# 08 Java 8 核心特性

## 本章目标

- 掌握 Java 8 中最常用、最实用的语言能力。
- 理解 Lambda、Stream、Optional、java.time 的价值与边界。
- 避免“写法很酷但团队难维护”的函数式滥用。

## 前置知识

- 已掌握集合与基础库用法。
- 会写基本面向对象代码。

## 一、Java 8 改变了什么

### 1) 从“命令式”到“声明式”

Java 8 提供了一种更清晰的表达方式：

- 关注“做什么”（过滤、映射、聚合）。
- 少写样板循环与临时变量。
- 让业务规则在代码中更直观。

### 2) 但不是所有代码都该函数式

- 简单逻辑可用 Stream 提升可读性。
- 复杂分支和副作用多的逻辑，仍可能用普通循环更清晰。
- 原则：以团队可读性为第一优先级。

## 二、四个核心能力

1. **Lambda**：把行为当参数传递。
2. **Stream**：声明式处理集合流水线。
3. **Optional**：显式表达“可能为空”的返回值语义。
4. **java.time**：现代时间 API，线程安全、语义清晰。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Order {
    private final long orderId;
    private final String userName;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    Order(long orderId, String userName, BigDecimal amount, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userName = userName;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    long getOrderId() {
        return orderId;
    }

    String getUserName() {
        return userName;
    }

    BigDecimal getAmount() {
        return amount;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
                new Order(1L, "alice", new BigDecimal("120.50"), LocalDateTime.now().minusMinutes(10)),
                new Order(2L, "", new BigDecimal("88.00"), LocalDateTime.now().minusMinutes(40)),
                new Order(3L, "bob", new BigDecimal("300.00"), LocalDateTime.now().minusMinutes(5))
        );

        // Stream: 过滤金额 >= 100 的订单，并提取用户名（忽略空值）
        List<String> targetUsers = orders.stream()
                .filter(o -> o.getAmount().compareTo(new BigDecimal("100")) >= 0)
                .map(Order::getUserName)
                .filter(name -> !name.trim().isEmpty())
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // Optional: 查找一个不存在的订单，给出默认值
        String displayUser = findOrderById(orders, 99L)
                .map(Order::getUserName)
                .filter(name -> !name.trim().isEmpty())
                .orElse("GUEST");

        // java.time: 判断订单是否超时（30 分钟）
        long timeoutCount = orders.stream()
                .filter(o -> Duration.between(o.getCreatedAt(), LocalDateTime.now()).toMinutes() > 30)
                .count();

        System.out.println("targetUsers=" + targetUsers);
        System.out.println("displayUser=" + displayUser);
        System.out.println("timeoutCount=" + timeoutCount);
    }

    private static Optional<Order> findOrderById(List<Order> orders, long orderId) {
        // Optional.ofNullable 表达“可能查不到”
        return orders.stream().filter(o -> o.getOrderId() == orderId).findFirst();
    }
}
```

**预期输出示例：**

```text
targetUsers=[ALICE, BOB]
displayUser=GUEST
timeoutCount=1
```

## 常见误区与修正

1. **误区：所有 for 循环都必须改成 Stream**  
   修正：复杂分支逻辑保留命令式写法可能更清晰。

2. **误区：Optional 代替所有 null 检查**  
   修正：Optional 重点是返回值语义，不是全场景替代。

3. **误区：Lambda 越短越好**  
   修正：可读性比“短”更重要。

## 面试高频问答

### Q1：Stream 为什么不建议滥用？

- 一句话结论：过长链式调用会降低可读性与可调试性。
- 关键点：
  1. 多层转换难断点排查。
  2. 业务分支复杂时语义不直观。
  3. 可拆分中间变量提升可读性。

### Q2：`map` 和 `flatMap` 区别？

- 一句话结论：`map` 是一层映射，`flatMap` 用于“展开再合并”。
- 关键点：
  1. `map` 可能得到嵌套结构。
  2. `flatMap` 常用于“多对多”展开。
  3. Optional 和 Stream 都有 `flatMap`。

## 练习与自测

1. 把示例中的“超时订单”改为返回订单 ID 列表。
2. 将一个多层判空流程改为 Optional 链式写法。
3. 解释：什么时候不用 Stream 而用普通循环更好。

## 本章小结

- Java 8 的核心价值是表达清晰，不是语法炫技。
- 函数式能力要服务业务可读性和维护性。

## 下一章

- [`09-Java9到25关键特性.md`](./09-Java9到25关键特性.md)
