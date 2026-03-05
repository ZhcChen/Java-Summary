# 07 JVM 基础与内存模型

## 本章目标

- 理解 Java 程序从源码到执行的核心路径。
- 建立对运行时内存区域和 GC 的基础认知。
- 形成“先看现象与指标，再做参数调整”的排障习惯。

## 前置知识

- 知道 `.java -> .class` 的编译流程。
- 理解对象创建和方法调用。

## 一、JVM 运行主链路

1. **类加载**：把字节码加载到 JVM。
2. **字节码执行**：解释执行 + JIT 编译热点代码。
3. **对象分配与回收**：对象进入堆，GC 回收不可达对象。

## 二、运行时内存区域（基础版）

- **堆（Heap）**：对象实例和数组主要分配区域。
- **虚拟机栈（Stack）**：每个线程私有，存放栈帧与局部变量。
- **方法区（Metaspace）**：类元数据、常量池等。
- **程序计数器**：线程当前执行位置。

> 排障常见误区：看到内存升高就直接加堆。正确做法是先判断“是否泄漏”“对象生命周期是否异常”“流量是否突增”。

## 三、GC 观察的最小指标集合

- GC 次数与暂停时间（尤其 Full GC）。
- 堆使用率变化趋势。
- 新生代晋升速度。
- 吞吐与响应时间抖动。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.util.ArrayList;
import java.util.List;

class UserCache {
    // 静态代码块用于演示类加载时机
    static {
        System.out.println("UserCache loaded");
    }
}

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        printMemory("before", runtime);

        // 访问类，触发类加载演示
        UserCache cache = new UserCache();
        if (cache == null) {
            System.out.println("never happen");
        }

        // 分配一批对象，观察内存变化
        List<byte[]> blocks = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            blocks.add(new byte[1024 * 30]);
        }

        printMemory("after alloc", runtime);

        // 清理引用，允许 GC 回收
        blocks.clear();
        System.gc(); // 仅演示，不建议在生产依赖手动 GC

        printMemory("after gc", runtime);
    }

    private static void printMemory(String phase, Runtime runtime) {
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        System.out.println(phase + " total=" + total + ", used=" + used + ", free=" + free);
    }
}
```

**预期输出示例：**

```text
before total=..., used=..., free=...
UserCache loaded
after alloc total=..., used=..., free=...
after gc total=..., used=..., free=...
```

## 常见误区与修正

1. **误区：频繁 Full GC 就调大堆**  
   修正：先定位对象分配和生命周期问题，再评估参数。

2. **误区：`System.gc()` 是常规优化手段**  
   修正：会带来停顿风险，不应作为常规策略。

3. **误区：只看 CPU，不看 GC 指标**  
   修正：JVM 问题要结合吞吐、延迟、GC 一起看。

## 面试高频问答

### Q1：堆和栈的核心区别？

- 一句话结论：栈存方法执行上下文，堆存对象实例。
- 关键点：
  1. 栈线程私有，生命周期随方法调用。
  2. 堆线程共享，主要由 GC 管理。
  3. 排障时两者问题表现不同。

### Q2：线上 JVM 排障第一步做什么？

- 一句话结论：先收集证据（指标、日志、快照），再下结论。
- 关键点：
  1. 观察 GC 停顿与频率。
  2. 查看堆增长趋势是否持续。
  3. 对照发布变更与流量变化。

## 练习与自测

1. 用示例代码分别创建 100/1000/5000 个对象块，比较内存变化。
2. 梳理一次“疑似 OOM”排障流程，写出 5 步检查项。
3. 解释：为什么 JVM 调优不能只靠参数经验。

## 本章小结

- JVM 基础是后续并发与性能调优的底座。
- 调优前先诊断，诊断前先拿数据。

## 下一章

- [`08-Java8核心特性.md`](./08-Java8核心特性.md)
