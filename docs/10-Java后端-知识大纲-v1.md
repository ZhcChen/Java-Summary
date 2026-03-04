# Java 后端知识大纲（v1）

> 目标：用于指导后续索引与正文编写，覆盖初级 / 中级 / 高级 + 面试场景。
> 版本：2026-03-05（基于 GitHub 开源仓库调研）

## 1. 调研样本与结论

### 1.1 主要参考仓库（GitHub）

| 仓库 | Star（调研时） | 最近活跃 | 特点 | 适合用途 |
|---|---:|---|---|---|
| [Snailclimb/JavaGuide](https://github.com/Snailclimb/JavaGuide) | 154k | 2026-03-04 | 覆盖最广，面试导向强，结构完整 | 作为主干目录参考 |
| [doocs/advanced-java](https://github.com/doocs/advanced-java) | 78k | 2026-01-21 | 高并发/分布式/高可用深入 | 作为中高级专题深挖参考 |
| [itwanger/toBeBetterJavaer](https://github.com/itwanger/toBeBetterJavaer) | 16k | 2026-02-22 | 讲解通俗，学习路径友好 | 作为初中级表达与讲解风格参考 |
| [doocs/source-code-hunter](https://github.com/doocs/source-code-hunter) | 23k | 2026-01-09 | 源码层剖析（Spring/Netty/Mybatis） | 作为“原理深挖”参考 |
| [kamranahmedse/developer-roadmap](https://github.com/kamranahmedse/developer-roadmap) | 350k | 2026-03-04 | 路线图表达优秀，导航感强 | 作为路线图与索引形式参考 |

### 1.2 调研观察（用于大纲排序）

- JavaGuide 在 `java/database/cs-basics/system-design/distributed` 等模块覆盖较均衡，适合做主线。
- advanced-java 在 `high-concurrency/distributed-system/high-availability` 侧更聚焦，适合做中高阶专题。
- source-code-hunter 在 Spring、Netty、Mybatis 等源码层内容更重，适合作为高级补充。
- 开源仓库普遍问题：内容多但“学习顺序 + 面试顺序 + 版本边界”往往分离，读者容易迷路。

## 2. 大纲排序原则

1. **先可用，再可解释，再可设计**：先能做业务，再能讲原理，最后能做架构取舍。
2. **先高频，再深挖**：每个阶段优先 P0/P1 高频题，再扩展 P2。
3. **先主线，再分支**：先打通 Java+Spring+MySQL+Redis 主链路，再扩展 MQ/分布式/高可用。
4. **先单体，再分布式**：避免在基础不稳时过早进入复杂架构。

## 3. Java 后端总大纲（初 / 中 / 高）

## L1 初级：业务开发与基础表达

### A. Java 基础与集合
- 语法、OOP、异常、泛型、反射、注解
- 集合框架（List/Map/Set）与复杂度
- 常见题：HashMap、ArrayList vs LinkedList、equals/hashCode

### B. 并发入门
- 线程生命周期、synchronized、volatile、ThreadLocal
- 线程池基础参数与使用场景

### C. JVM 入门
- 内存区域、类加载、对象创建、GC 基础概念

### D. Web 与 Spring Boot
- HTTP/HTTPS 基础、REST 规范、参数校验、统一异常
- Spring Boot 快速开发链路（Controller-Service-DAO）

### E. 数据层基础
- MySQL：索引、事务、隔离级别、慢 SQL 入门
- Redis：数据结构、过期策略、缓存基础模式

### F. 工程化基础
- Git、Maven/Gradle、日志、基本测试、接口文档

## L2 中级：性能优化与系统稳定性

### A. 并发进阶
- CAS、AQS、锁优化、并发容器
- 线程池参数调优、拒绝策略、监控指标

### B. JVM 调优与排障
- 常见 GC 策略、内存泄漏定位、OOM 排查路径
- 分析工具与日志定位思路

### C. Spring 原理
- IOC/AOP/事务传播机制
- Spring MVC 请求链路与常见性能瓶颈

### D. 数据库与缓存优化
- MySQL 执行计划、索引失效、SQL 重写
- 缓存穿透/击穿/雪崩、双写一致性、分布式锁

### E. 消息队列与异步架构
- Kafka/RabbitMQ/RocketMQ 基础模型
- 丢失、重复、顺序、积压治理

### F. 微服务与治理基础
- 注册发现、配置中心、网关、限流熔断降级
- 链路追踪、日志聚合、核心监控指标

## L3 高级：架构设计与治理能力

### A. 高并发系统设计
- 流量模型、热点隔离、读写分离、削峰填谷
- 容量估算与压测方法

### B. 分布式一致性与事务
- CAP/BASE 理解
- 2PC/TCC/SAGA/本地消息表等方案取舍

### C. 高可用与稳定性工程
- 多副本、多机房、容灾与故障演练
- 限流/熔断/降级/隔离的系统化治理

### D. 可观测性与运维协同
- Metrics/Logs/Trace 三位一体
- SLA/SLO/错误预算与告警降噪

### E. 架构演进与技术治理
- 单体到微服务演进策略
- 技术债治理、规范建设、性能基线

## 4. 面试索引分层（用于后续文档）

- **P0（必问）**：基础概念 + 高频场景（初中级面试核心）
- **P1（高频进阶）**：性能优化 + 稳定性 + 中间件
- **P2（拉开差距）**：架构设计 + 复杂故障 + 取舍题

建议每个主题都标注：`层级(L1/L2/L3)` + `优先级(P0/P1/P2)` + `专题标签`。

## 5. 索引文档编写标准（下一步执行）

后续索引页建议统一字段：
- 编号
- 主题
- 层级
- 优先级
- 前置知识
- 核心产出（图 / 问答 / 示例）
- 状态（TODO/DOING/DONE）

## 6. 我的建议（落地策略）

1. 以本大纲作为唯一主干，避免后续重复造目录。
2. 先补 L1 的 P0 主题（最快形成可复习闭环）。
3. L2/L3 先补“高频题 + 经典场景题”，再补全理论。
4. 每篇文档坚持固定模板：结论 -> 原理图 -> 场景 -> 问答 -> 代码。

## 7. 已落地文档映射（2026-03-05）

### L1
- `docs/L1-初级/01-Java基础与集合.md`
- `docs/L1-初级/02-并发与JVM入门.md`
- `docs/L1-初级/03-SpringBoot-MySQL-Redis开发主线.md`
- `docs/L1-初级/04-L1高频面试题与答题模板.md`

### L2
- `docs/L2-中级/01-并发进阶与线程池调优.md`
- `docs/L2-中级/02-JVM调优与线上排障.md`
- `docs/L2-中级/03-MySQL-Redis-MQ性能与可靠性.md`
- `docs/L2-中级/04-微服务治理与可观测性.md`

### L3
- `docs/L3-高级/01-高并发系统设计与容量规划.md`
- `docs/L3-高级/02-分布式一致性与事务方案.md`
- `docs/L3-高级/03-高可用与稳定性工程.md`
- `docs/L3-高级/04-架构演进与技术治理面试.md`

## 8. 学习与复习配套文档

- `docs/12-六周学习执行计划.md`
- `docs/13-面试题库编号与复习规则.md`
- `docs/L1-初级/00-L1子章节索引.md`
- `docs/L2-中级/00-L2子章节索引.md`
- `docs/L3-高级/00-L3子章节索引.md`

## 9. 本轮细化进展（2026-03-05）

- L1-M1 子章节（S01~S06）已全部落地，见 `docs/L1-初级/00-L1子章节索引.md`。
