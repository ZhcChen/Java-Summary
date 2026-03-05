# L1-01 Java 基础与集合

> 本章是 L1 的起点。目标不是“背 API”，而是建立稳定的 Java 语言认知和集合选型思维。

## 本章定位

- 你将学会：值传递、对象模型、集合框架、选型依据。
- 你将避免：只会写代码但说不清为什么这样写。
- 你将产出：1 份集合选型说明 + 1 个可运行示例。

## 前置知识

- 会写 `main` 方法。
- 知道变量、条件、循环、方法的基本语法。
- 对 List/Set/Map 有初步印象（不要求深入）。

## 学习路径（建议顺序）

1. 先理解“值传递”和“对象引用”的关系。
2. 再理解“对象设计与接口”如何影响代码可维护性。
3. 最后学习集合选型：为什么在不同场景选不同结构。

## 一、Java 只有值传递（重点）

### 1.1 定义

Java 方法参数传递时，传递的一定是“值的副本”。

- 基本类型：传递字面值副本。
- 引用类型：传递“引用地址值”的副本。

### 1.2 为什么容易误解

因为“引用副本”仍然指向同一个对象，所以你在方法内部改对象字段，方法外能看到变化；这会让人误以为是“引用传递”。

### 1.3 正确结论

- 能改对象内部状态（同一对象）。
- 不能改调用方变量本身指向（不同引用变量）。

## 二、对象设计与接口思维（入门）

### 2.1 面向对象不是“会写 class”

真正有价值的是“按职责拆分”：

- 一个类只做一类事。
- 接口定义能力边界。
- 调用方依赖抽象，不依赖具体实现细节。

### 2.2 为什么这和基础有关

后续学 Spring、微服务、分层架构，本质都在做“职责分离 + 依赖抽象”。基础阶段不打牢，后面只会机械套模板。

## 三、集合框架选型（最常用）

### 3.1 常见结构与场景

| 场景 | 推荐 | 理由 |
|---|---|---|
| 按下标读取频繁 | `ArrayList` | 连续内存，读取快 |
| 键值查找 | `HashMap` | 平均 O(1) 查找 |
| 元素去重 | `HashSet` | 基于哈希的去重能力 |
| 需要有序键 | `TreeMap` | 支持按键排序和范围查询 |
| 并发读写 Map | `ConcurrentHashMap` | 比 `HashMap` 更安全 |

### 3.2 常见错误

- 只背复杂度，不看真实访问模式。
- 在并发场景继续使用 `HashMap`。
- 用 `==` 比较包装类型，导致结果不稳定。

## 四、常见误区与修正

### 误区 1：`LinkedList` 一定比 `ArrayList` 插入快

修正：理论上链表中间插入是 O(1)，但现实中需要先定位节点，且 CPU 缓存不友好，业务场景多数仍是 `ArrayList` 更实用。

### 误区 2：会背集合 API 就算掌握

修正：面试和工程都看“选型理由 + 边界条件 + 风险控制”。

## 五、练习任务

1. 给“订单列表展示、用户去重、配置项查询”三个场景做集合选型。
2. 为每个场景写出“为什么不选另一种结构”的一句话说明。
3. 把下面示例代码跑通并用自己的话解释每条输出。

## 六、错答与修正（章级题解）

### 题目

`ArrayList`、`LinkedList`、`HashMap`、`ConcurrentHashMap` 在业务中如何选？

### 错答示例（低分）

- “看复杂度就行，`LinkedList` 插入快所以优先用它。”
- “`HashMap` 快，默认都用它。”

### 修正答法（高分）

1. 先按访问模式分场景：随机读、中间插入、键值查询、并发写。
2. 再说结构差异：数组连续内存、哈希冲突、并发安全机制。
3. 最后说边界：并发场景下 `HashMap` 的风险和替代方案。

### 打分差异

| 维度 | 低分答法 | 高分答法 |
|---|---|---|
| 概念准确 | 只背复杂度 | 复杂度 + 实际访问模式 |
| 场景匹配 | 一刀切 | 分场景选择 |
| 风险意识 | 不提并发风险 | 说明线程安全边界 |

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    // 用于演示“引用副本”传参
    static class User {
        String name;

        User(String name) {
            this.name = name;
        }
    }

    public static void renameInsideMethod(User user) {
        // 这里修改的是“同一个对象”的字段，调用方可见
        user.name = "renamed-in-method";

        // 注意：下面这句只是修改“方法内局部变量 user 的指向”
        // 并不会影响 main 方法里 originalUser 变量本身的指向
        user = new User("new-object");

        // 此处打印 new-object，只在本方法内有效
        System.out.println("method user name = " + user.name);
    }

    public static void main(String[] args) {
        // 1) 值传递 + 引用副本示例
        User originalUser = new User("alice");
        renameInsideMethod(originalUser);
        // 输出 renamed-in-method，证明对象字段被修改
        System.out.println("main user name = " + originalUser.name);

        // 2) 集合选型示例：顺序列表使用 ArrayList
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("backend");

        // 3) 键值查询场景使用 HashMap
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("bob", 95);
        scoreMap.put("tom", 88);

        // 4) 输出结果，便于验证行为
        System.out.println("first tag = " + tags.get(0));
        System.out.println("bob score = " + scoreMap.get("bob"));
    }
}
```

**预期输出示例：**

```text
method user name = new-object
main user name = renamed-in-method
first tag = java
bob score = 95
```

## 关联阅读（本仓库）

- [`11-L1-M1-S01-数据类型与包装类.md`](./11-L1-M1-S01-数据类型与包装类.md)
- [`13-L1-M1-S03-OOP与接口设计基础.md`](./13-L1-M1-S03-OOP与接口设计基础.md)
- [`16-L1-M1-S06-集合选型与复杂度.md`](./16-L1-M1-S06-集合选型与复杂度.md)
