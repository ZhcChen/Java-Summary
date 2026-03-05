# 07 JVM 基础与内存模型

## 本章目标

- 理解 Java 程序从源码到运行的大致过程。
- 认识常见内存区域及问题定位方向。
- 建立“先观察再调参”的排障习惯。

## 一、程序运行路径（简化）

1. 源码 `.java` 编译为字节码 `.class`。
2. 类加载器加载字节码到 JVM。
3. 解释执行或 JIT 编译后执行。
4. GC 负责不可达对象回收。

## 二、运行时内存区域

| 区域 | 作用 | 常见问题 |
|---|---|---|
| 堆 | 对象实例存储 | 堆 OOM |
| 栈 | 方法调用栈帧 | 栈溢出 |
| 方法区/元空间 | 类元数据 | 元空间不足 |
| 程序计数器 | 线程执行位置 | 通常无需直接调优 |

## 三、GC 基础认知

- 年轻代：新创建对象，回收频率高。
- 老年代：存活时间长的对象。
- Full GC 频繁通常意味着内存压力或对象生命周期异常。

## 四、排障最小流程

1. 先看现象：延迟、错误率、CPU、内存。
2. 再看 JVM 指标：堆占用、GC 次数、停顿时间。
3. 最后结合代码定位：对象创建热点、缓存泄漏、集合膨胀。

## 五、练习任务

1. 启动一个程序并观察 `Runtime` 输出的内存信息。
2. 模拟创建大量对象，观察 GC 前后可用内存变化。
3. 写一份“JVM 问题定位 5 步清单”。

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

        // 分配一批对象，模拟业务中的内存占用增长
        List<byte[]> buffers = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            buffers.add(new byte[1024 * 50]); // 每个约 50KB
        }

        long totalAfterAlloc = runtime.totalMemory();
        long freeAfterAlloc = runtime.freeMemory();

        // 清空引用并建议 GC（仅用于演示，生产代码不应依赖显式 GC）
        buffers.clear();
        System.gc();

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

## 返回

- [`README.md`](./README.md)
