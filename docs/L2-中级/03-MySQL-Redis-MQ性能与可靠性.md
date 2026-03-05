# L2-03 MySQL / Redis / MQ 性能与可靠性

## 这是什么

这是中级面试最常见的“中间件三件套”章节：
- MySQL：慢查询优化
- Redis：缓存一致性与高并发缓存问题
- MQ：消息可靠性与幂等

## 关联关系图

```mermaid
flowchart LR
    A[应用服务] --> B[Redis缓存]
    A --> C[MySQL存储]
    A --> D[MQ异步化]
    D --> E[下游消费者]
```

## 核心知识点

### 1) MySQL 优化

- 先看执行计划（`EXPLAIN`），识别全表扫描和回表开销。
- 组合索引遵循最左前缀匹配。
- SQL 优化要结合业务访问模式，不只看单条语句。

### 2) Redis 一致性

- 常见模式：先更新 DB，再删除缓存。
- 缓存问题：穿透、击穿、雪崩。
- 常见治理：布隆过滤、互斥锁、随机过期、限流降级。

示例：[`../../examples/l2/CacheAsideDemo.java`](../../examples/l2/CacheAsideDemo.java)

### 3) MQ 可靠性

- 生产者确认 + Broker 持久化 + 消费者手动确认。
- 幂等消费：业务唯一键 / 去重表 / token 机制。
- 顺序消费：按 key 分区，保证同 key 顺序。

## 高频面试题

### Q1：如何保证消息不丢失？

答题骨架：
1. 生产端：发送确认与失败重试。
2. Broker：持久化与副本机制。
3. 消费端：消费确认与重试死信。

### Q2：缓存和数据库一致性怎么保证？

答题骨架：
1. 先更新 DB，再删缓存。
2. 处理并发读写时序问题（延迟双删或订阅 binlog）。
3. 明确一致性目标（强一致/最终一致）。

## 延伸阅读

- [advanced-java - 缓存与 MQ](https://github.com/doocs/advanced-java/tree/main/docs/high-concurrency)
- [JavaGuide - 数据库与消息队列](https://github.com/Snailclimb/JavaGuide/tree/main/docs/database)


## 前置知识

- 会写基本 SQL。
- 知道主键和索引概念。

## 术语解释（零基础友好）

- **执行计划**：数据库选择语句执行路径的说明。
- **索引失效**：优化器未走预期索引导致扫描增加。

## 详细学习步骤（从不会到会）

1. 先看执行计划定位瓶颈。
2. 重写 SQL 或调整索引。
3. 优化后做对比验证。

## 常见错误与纠偏

- 只加索引不看查询模式。
- 优化后不做回归验证。

## 学习动作

- 先手敲一次示例代码，确保可以独立运行。
- 用自己的话复述“定义 -> 原理 -> 场景 -> 边界”。
- 把本节关键结论写成 3 句速记卡，第二天复盘。

## 练习任务（建议动手）

1. 给慢 SQL 设计优化方案并解释。
2. 写出两种索引失效案例。

## 练习参考方向

- 优化一定要“前后对比+可复现”。

## 复习检查

- [ ] 能在 90 秒内说明本节核心结论
- [ ] 能独立运行并解释示例代码输出
- [ ] 能说出至少 1 个常见错误与修正方式

## Java 示例代码（含注释，可直接运行）


**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

**预期输出（示例）：**
```text
SELECT id, name FROM user WHERE phone = ?
```

```java
public class Main {
    public static void main(String[] args) {
        // 索引友好写法：避免在索引列上做函数操作
        String sql = "SELECT id, name FROM user WHERE phone = ?";
        // 实战中应结合 EXPLAIN 验证执行计划
        System.out.println(sql);
    }
}
```
