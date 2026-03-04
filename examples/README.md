# 示例代码索引

## L1 初级
- `l1/ValuePassingDemo.java`：值传递与引用对象修改示例
- `l1/DataTypeAndWrapperDemo.java`：基本类型、包装类型与拆装箱坑
- `l1/StringBigDecimalPitfallsDemo.java`：String 处理与 BigDecimal 精度坑
- `l1/ExceptionPracticeDemo.java`：业务异常抛出与捕获示例
- `l1/GenericsWildcardDemo.java`：泛型通配符与 PECS 示例
- `l1/CollectionSelectionDemo.java`：集合选型与有序/去重行为演示
- `basic/ThreadPoolQuickDemo.java`：线程池快速上手

## L2 中级
- `l2/ThreadPoolSizingDemo.java`：线程池参数与运行指标观察
- `l2/CacheAsideDemo.java`：缓存旁路模式（Cache Aside）最小实现

## L3 高级
- `l3/IdempotencyTokenDemo.java`：幂等 token 去重与 TTL 过期演示

## 使用方式

在项目根目录执行：

```bash
javac examples/l1/DataTypeAndWrapperDemo.java && java -cp examples/l1 DataTypeAndWrapperDemo
```

你也可以替换为其他示例文件路径进行编译运行。
