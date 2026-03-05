# 05 IO、NIO 与文件处理

## 本章目标

- 理解 IO 与 NIO 在工程中的适用边界。
- 掌握编码、资源关闭、流式处理的关键实践。
- 能写出可移植、可维护的文件处理代码。

## 前置知识

- 会用字符串和集合。
- 知道异常处理的基本写法。
- 理解“读取 -> 处理 -> 输出”的流程。

## 一、IO 与 NIO 怎么理解

### 1) 两种模型的差异

- **IO（Stream）**：以输入流/输出流为中心，概念简单，适合基础场景。
- **NIO（Channel + Buffer + Path/Files）**：工具更现代，组合性更好，更适合工程代码。

### 2) 真实项目里常见做法

- 文本文件优先 `Path + Files + BufferedReader`。
- 小文件可一次性读取，大文件必须流式处理。
- 所有字符读写都显式指定 UTF-8。

## 二、文件处理三条硬规则

1. **编码显式指定**：避免跨环境乱码。
2. **资源自动关闭**：优先 `try-with-resources`。
3. **中间结果可观测**：处理行数、错误行数、输出路径要可追踪。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Path input = Files.createTempFile("java-summary-input-", ".txt");
        Path output = Files.createTempFile("java-summary-output-", ".txt");

        // 准备一份模拟导入文件（词频统计）
        Files.write(input,
                java.util.Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple"),
                StandardCharsets.UTF_8);

        Map<String, Integer> counter = new LinkedHashMap<>();

        // try-with-resources: 任何异常分支都能确保资源被关闭
        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (word.isEmpty()) {
                    continue;
                }
                // merge 简化“存在则+1，不存在则初始化”逻辑
                counter.merge(word, 1, Integer::sum);
            }
        }

        // 将统计结果输出到新文件
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> entry : counter.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }

        System.out.println("inputFile=" + input.getFileName());
        System.out.println("outputFile=" + output.getFileName());
        System.out.println("wordKinds=" + counter.size());
        System.out.println("appleCount=" + counter.get("apple"));

        // 示例结束后清理临时文件，避免污染本地目录
        Files.deleteIfExists(input);
        Files.deleteIfExists(output);
    }
}
```

**预期输出示例：**

```text
inputFile=java-summary-input-xxxx.txt
outputFile=java-summary-output-xxxx.txt
wordKinds=3
appleCount=3
```

## 常见误区与修正

1. **误区：默认编码够用了**  
   修正：默认编码依赖环境，必须显式指定 UTF-8。

2. **误区：小文件写法可直接复制到大文件场景**  
   修正：大文件应流式处理，避免内存峰值过高。

3. **误区：finally 手动关资源就行**  
   修正：`try-with-resources` 更安全、可读性更高。

## 面试高频问答

### Q1：`Files.readAllLines` 什么时候不建议使用？

- 一句话结论：在大文件或高并发场景不建议一次性读入内存。
- 关键点：
  1. 内存峰值不可控。
  2. 可能触发频繁 GC。
  3. 流式读取更稳。

### Q2：为什么推荐 `Path` 而不是 `File`？

- 一句话结论：`Path` + `Files` API 更现代、语义更清晰、组合能力更强。
- 关键点：
  1. 与 NIO 生态一致。
  2. 工具方法丰富。
  3. 代码可读性更好。

## 练习与自测

1. 将示例扩展为“忽略大小写词频统计”。
2. 新增错误行收集（如空行、非法字符）并输出报告文件。
3. 解释：为什么文件处理代码必须显式记录输入、输出和处理条数。

## 本章小结

- 文件处理稳定性的本质在细节：编码、资源、流式与可观测。
- NIO 工具链是现代 Java 文件处理主力组合。

## 下一章

- [`06-并发基础与线程池.md`](./06-并发基础与线程池.md)
