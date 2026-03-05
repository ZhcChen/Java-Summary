# 07 JVM 基础与内存模型

## 本章目标

- 理解 Java 程序在 JVM 中的基本运行路径。
- 认识常见内存区域和问题定位方向。
- 建立“指标先行”的 JVM 问题分析习惯。

## 前置知识

- 知道 `.java` 编译为 `.class` 的过程。
- 对对象和方法调用有基本理解。

## 核心概念

1. 类加载：字节码进入 JVM 的入口。
2. 运行时内存区域：堆、栈、方法区等。
3. 垃圾回收：回收不可达对象，释放内存。

## 原理展开

- 程序执行性能与内存模型密切相关。
- Full GC 频繁通常不是“GC 问题本身”，而是对象生命周期或容量配置问题。
- 排障应基于现象与指标，而非直接调参。

## 典型场景

- 服务响应抖动，伴随 GC 停顿升高。
- 堆占用持续增长，最终 OOM。
- 发布后吞吐下降，需要确认 JIT 与对象分配热点。

## 常见误区

1. 看到内存高就直接加堆，不定位根因。
2. 不看 GC 日志与指标，只靠主观猜测。
3. 把 `System.gc()` 当成常规优化手段。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        long totalBefore = runtime.totalMemory();
        long freeBefore = runtime.freeMemory();

        // 分配一批对象，模拟内存占用增长
        List<byte[]> buffers = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            buffers.add(new byte[1024 * 50]);
        }

        long totalAfterAlloc = runtime.totalMemory();
        long freeAfterAlloc = runtime.freeMemory();

        buffers.clear();
        System.gc(); // 仅演示，生产不建议依赖显式 GC

        long totalAfterGc = runtime.totalMemory();
        long freeAfterGc = runtime.freeMemory();

        System.out.println("before total=" + totalBefore + ", free=" + freeBefore);
        System.out.println("after alloc total=" + totalAfterAlloc + ", free=" + freeAfterAlloc);
        System.out.println("after gc total=" + totalAfterGc + ", free=" + freeAfterGc);
    }
}
```

**预期输出示例：**

```text
before total=...
after alloc total=..., free=...
after gc total=..., free=...
```

## 练习与自测

1. 记录一次程序运行中的堆变化并解释原因。
2. 写出“JVM 问题定位 5 步法”。
3. 自测：能否说清堆、栈、方法区各自职责。

## 本章小结

- JVM 问题要从数据出发，而不是凭经验拍脑袋。
- 理解内存模型是后续调优与排障的基础。

## 下一章

- [`08-Java8核心特性.md`](./08-Java8核心特性.md)
