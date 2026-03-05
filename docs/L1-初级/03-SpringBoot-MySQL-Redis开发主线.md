# L1-03 Spring Boot + MySQL + Redis 开发主线

## 这是什么

这章把“后端日常开发主链路”串起来：
- Web 接口开发（Spring Boot）
- 持久化（MySQL）
- 缓存提速（Redis）

## 主链路流程图

```mermaid
sequenceDiagram
    participant C as Client
    participant A as App(Spring Boot)
    participant R as Redis
    participant M as MySQL

    C->>A: HTTP 请求
    A->>R: 先查缓存
    alt 缓存命中
        R-->>A: 返回数据
    else 缓存未命中
        A->>M: 查询数据库
        M-->>A: 返回结果
        A->>R: 写入缓存
    end
    A-->>C: 返回响应
```

## 关键知识点

### 1) Spring Boot 基础

- 分层结构：Controller / Service / Repository
- 参数校验：`@Valid`
- 异常处理：`@RestControllerAdvice`

### 2) MySQL 基础

- 索引本质：减少扫描行数
- 事务 ACID 与隔离级别
- `EXPLAIN` 看执行计划

### 3) Redis 基础

- 常用结构：String、Hash、List、Set、ZSet
- 典型模式：Cache Aside（旁路缓存）

示例：[`../../examples/l2/CacheAsideDemo.java`](../../examples/l2/CacheAsideDemo.java)

## 常见误区

- 误区 1：有索引就一定快。  
  实际：回表、低选择性、函数操作列都可能导致慢查询。
- 误区 2：缓存更新必须“先删缓存再更数据库”。  
  实际：更常见推荐是“先更新数据库再删缓存”。

## 高频面试题

### Q1：为什么要用 Redis 缓存？

答题骨架：
1. 目标：降低数据库压力、缩短响应时间。
2. 手段：热点数据缓存、减少重复查询。
3. 风险：一致性问题、缓存穿透/击穿/雪崩。
4. 方案：限流、布隆过滤、互斥重建、多级缓存。

### Q2：事务隔离级别怎么选？

答题骨架：
1. 先说四个隔离级别。
2. 明确默认级别（MySQL InnoDB：`REPEATABLE READ`）。
3. 按业务一致性要求与性能成本权衡。

## 延伸阅读

- [JavaGuide - 数据库](https://github.com/Snailclimb/JavaGuide/tree/main/docs/database)
- [JavaGuide - Redis](https://github.com/Snailclimb/JavaGuide/tree/main/docs/database/redis)


## 前置知识

- 理解 HTTP 请求流程。
- 会写基础 Java 类。

## 术语解释（零基础友好）

- **分层**：按职责拆分控制层、业务层、数据层。
- **治理**：统一规范和监控确保可维护。

## 详细学习步骤（从不会到会）

1. 先搭最小功能链路。
2. 抽离公共校验和异常处理。
3. 验证扩展时对旧代码影响最小。

## 常见错误与纠偏

- 职责边界混乱。
- 公共逻辑重复分散。

## 学习动作

- 先手敲一次示例代码，确保可以独立运行。
- 用自己的话复述“定义 -> 原理 -> 场景 -> 边界”。
- 把本节关键结论写成 3 句速记卡，第二天复盘。

## 练习任务（建议动手）

1. 按三层实现一个查询接口。
2. 补充统一异常处理并验证返回格式。

## 练习参考方向

- 分层目标是降低维护成本与认知负担。

## 复习检查

- [ ] 能在 90 秒内说明本节核心结论
- [ ] 能独立运行并解释示例代码输出
- [ ] 能说出至少 1 个常见错误与修正方式

## Java 示例代码（含注释，可直接运行）


**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

**预期输出（示例）：**
```text
user-7
```

```java
class UserController {
    private final UserService userService = new UserService();

    String getUser(Long id) {
        // Controller 负责入口边界
        return userService.findNameById(id);
    }
}

class UserService {
    String findNameById(Long id) {
        // Service 负责业务逻辑
        return "user-" + id;
    }
}

public class Main {
    public static void main(String[] args) {
        UserController c = new UserController();
        System.out.println(c.getUser(7L));
    }
}
```
