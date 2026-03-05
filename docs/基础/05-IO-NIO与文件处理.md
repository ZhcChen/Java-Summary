# 05 IO、NIO 与文件处理

## 本章目标

- 理解传统 IO 与 NIO 的基本差异。
- 会用 `java.nio.file` 写出可靠文件读写逻辑。
- 避免资源泄漏、编码混乱和路径处理错误。

## 一、IO 与 NIO 的核心差异

- IO：以流（Stream）为中心。
- NIO：以缓冲区（Buffer）和通道（Channel）为中心。

在业务代码中，最常用的是 `java.nio.file.Path` 与 `Files` 工具类。

## 二、文件处理三原则

1. 始终显式指定字符集（如 UTF-8）。
2. 使用 `try-with-resources` 自动关闭资源。
3. 对路径、权限、文件不存在等错误做明确处理。

## 三、常见错误

- 默认字符集导致跨环境乱码。
- 手动关闭流时遗漏异常分支。
- 把大文件一次性全部读入内存。

## 四、练习任务

1. 读取一个文本文件，统计行数并输出。
2. 把输入文本按行去重后写入新文件。
3. 故意传入不存在路径，验证异常信息可读性。

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
        // 1) 创建临时文件，避免污染工作目录
        Path tempFile = Files.createTempFile("java-summary-", ".txt");

        // 2) 显式使用 UTF-8 写入内容
        List<String> linesToWrite = List.of("alpha", "beta", "beta", "gamma");
        Files.write(tempFile, linesToWrite, StandardCharsets.UTF_8);

        // 3) 读取文件并输出行数
        List<String> linesRead = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        long distinctCount = linesRead.stream().distinct().count();

        System.out.println("file=" + tempFile.getFileName());
        System.out.println("lineCount=" + linesRead.size());
        System.out.println("distinctCount=" + distinctCount);

        // 4) 示例结束后删除临时文件，保持环境整洁
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

## 返回

- [`README.md`](./README.md)
