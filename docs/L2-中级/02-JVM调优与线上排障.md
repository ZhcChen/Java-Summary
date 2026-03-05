# L2-02 JVM 调优与线上排障

## 这是什么

本章目标是形成“线上可执行”的 JVM 排障流程：
- GC 指标识别
- OOM 定位
- 参数优化闭环

## 排障流程图

```mermaid
flowchart LR
    A[症状: RT升高/CPU高/FullGC频繁] --> B[采集证据: GC日志/线程栈/堆快照]
    B --> C[定位问题: 内存泄漏/对象晋升过快/分配速率过高]
    C --> D[优化动作: 调参数/改代码/控流量]
    D --> E[回归验证: 指标恢复]
```

## 核心方法

### 1) 先证据后结论

先拿到：
- GC 日志
- 堆 dump
- 线程 dump

避免“凭感觉调参数”。

### 2) 常见场景

- Full GC 频繁：关注老年代增长速度、对象晋升、内存泄漏。
- Young GC 过于频繁：关注对象创建峰值与短命对象比例。
- OOM：先判断是堆、元空间还是直接内存问题。

### 3) 参数优化原则

- 先小步调整，再观测指标变化。
- 不同 JDK 版本、不同 GC 策略参数差异明显，必须标注版本。

## 高频面试题

### Q1：Full GC 频繁怎么排查？

答题骨架：
1. 明确现象（频率、耗时、影响范围）。
2. 收集日志和快照证据。
3. 定位是流量突增、对象模型问题还是参数不匹配。
4. 给出短期止血和长期治理方案。

### Q2：你做过哪些 JVM 调优？

答题骨架：
1. 业务背景（高峰 QPS、RT 要求）。
2. 问题指标（GC 次数、耗时、内存占用）。
3. 调优动作（参数+代码）。
4. 结果数据（前后对比）。

## 延伸阅读

- [JavaGuide - JVM](https://github.com/Snailclimb/JavaGuide/tree/main/docs/java/jvm)
- [source-code-hunter - JDK/JVM 相关](https://github.com/doocs/source-code-hunter/tree/main/docs/JDK)

## Java 示例代码（含注释）

```java
public class JvmMemorySnippet {
    public static void main(String[] args) {
        int local = 1; // 栈上局部变量
        byte[] bytes = new byte[1024]; // 堆上对象
        // 类元数据位于方法区（JDK8+ 为元空间）
        System.out.println(local + bytes.length);
    }
}
```

