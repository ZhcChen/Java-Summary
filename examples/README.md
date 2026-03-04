# 示例代码索引

## L1 初级
- `l1/ValuePassingDemo.java`：值传递与引用对象修改示例
- `l1/DataTypeAndWrapperDemo.java`：基本类型、包装类型与拆装箱坑
- `l1/StringBigDecimalPitfallsDemo.java`：String 处理与 BigDecimal 精度坑
- `l1/ExceptionPracticeDemo.java`：业务异常抛出与捕获示例
- `l1/GenericsWildcardDemo.java`：泛型通配符与 PECS 示例
- `l1/CollectionSelectionDemo.java`：集合选型与有序/去重行为演示
- `l1/VolatileVisibilityDemo.java`：volatile 可见性最小演示
- `l1/JvmMemoryRegionDemo.java`：栈变量与堆对象演示
- `l1/ClassLoadingOrderDemo.java`：类加载与初始化顺序演示
- `basic/ThreadPoolQuickDemo.java`：线程池快速上手

## L2 中级
- `l2/ThreadPoolSizingDemo.java`：线程池参数与运行指标观察
- `l2/CacheAsideDemo.java`：缓存旁路模式（Cache Aside）最小实现
- `l2/CasAtomicDemo.java`：CAS 与 AtomicInteger 并发计数示例
- `l2/MqIdempotentConsumerDemo.java`：消息消费幂等去重示例
- `l2/TokenBucketDemo.java`：令牌桶限流最小实现
- `l2/TraceIdLogDemo.java`：traceId 贯穿日志示例

## L3 高级
- `l3/IdempotencyTokenDemo.java`：幂等 token 去重与 TTL 过期演示
- `l3/CapacityPlanningDemo.java`：容量估算计算示例
- `l3/CompensationSagaDemo.java`：SAGA 补偿流程示例

## 使用方式

在项目根目录执行：

```bash
javac examples/l2/CasAtomicDemo.java && java -cp examples/l2 CasAtomicDemo
```

你也可以替换为其他示例文件路径进行编译运行。
