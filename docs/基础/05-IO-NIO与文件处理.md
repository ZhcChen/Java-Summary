# 05 IO、NIO 与文件处理

## 本章目标

- 理解 IO 与 NIO 的主要差异。
- 掌握文件读写中的编码、资源管理与异常处理要点。
- 能写出可移植、可维护的基础文件处理逻辑。

## 前置知识

- 会使用字符串和集合。
- 理解异常处理的基本写法。

## 核心概念

1. IO 以流为中心，NIO 以缓冲区/通道为中心。
2. `Path + Files` 是现代 Java 文件处理常用组合。
3. `try-with-resources` 能显著降低资源泄漏风险。

## 原理展开

- 字符编码必须显式指定，否则跨环境容易乱码。
- 大文件场景不能一把梭 `readAllLines`，应流式处理。
- 临时文件和中间文件要有清理策略。

## 典型场景

- 配置文件读取。
- 导入文件校验与行处理。
- 日志切分文件分析。

## 常见误区

1. 使用平台默认编码。
2. 手动关闭资源遗漏异常分支。
3. 一次性读取大文件导致内存压力。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 创建临时文件，避免污染项目目录
        Path tempFile = Files.createTempFile("java-summary-", ".txt");

        // 显式使用 UTF-8，保证跨环境一致
        List<String> linesToWrite = List.of("alpha", "beta", "beta", "gamma");
        Files.write(tempFile, linesToWrite, StandardCharsets.UTF_8);

        List<String> linesRead = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        long distinctCount = linesRead.stream().distinct().count();

        System.out.println("file=" + tempFile.getFileName());
        System.out.println("lineCount=" + linesRead.size());
        System.out.println("distinctCount=" + distinctCount);

        // 示例结束后清理临时文件
        Files.deleteIfExists(tempFile);
    }
}
```

**预期输出示例：**

```text
file=java-summary-xxxx.txt
lineCount=4
distinctCount=3
```

## 练习与自测

1. 读取文件并统计每个单词出现次数。
2. 写一个“去重后导出新文件”的小工具。
3. 自测：能否解释为什么要显式指定 UTF-8。

## 本章小结

- 文件处理的稳定性来自细节：编码、资源、异常。
- NIO 工具类组合能显著提升代码清晰度。

## 下一章

- [`06-并发基础与线程池.md`](./06-并发基础与线程池.md)
