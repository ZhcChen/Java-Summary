# L1-03 SpringBoot + MySQL + Redis 开发主线

> 本章目标：把“写接口”变成一个完整工程链路，而不是只会写 Controller。

## 本章定位

- 你将学会：请求从入口到存储再到缓存的完整路径。
- 你将避免：分层混乱、异常四处散落、缓存使用无策略。
- 你将产出：1 条可运行的最小链路（Controller -> Service -> DB/Cache 模拟）。

## 前置知识

- 会写基础 Java 类。
- 知道 HTTP 请求与响应的概念。
- 知道 MySQL/Redis 分别用于持久化与缓存。

## 学习路径（建议顺序）

1. 先建立分层结构：Controller / Service / Repository。
2. 再补统一异常与参数校验，保证接口稳定输出。
3. 最后引入 Redis 旁路缓存，理解读写路径与一致性风险。

## 一、分层开发：先把职责划清

### Controller 层

- 负责接收参数、调用服务、返回结果。
- 不承担复杂业务逻辑。

### Service 层

- 负责业务规则、流程编排。
- 连接 Controller 和数据层。

### Repository 层

- 负责和存储系统交互（MySQL/Redis）。
- 不处理业务策略。

## 二、MySQL 与 Redis 的角色分工

| 组件 | 角色 | 典型优点 | 常见风险 |
|---|---|---|---|
| MySQL | 权威数据源 | 事务能力强、数据可靠 | 高并发读压力大 |
| Redis | 热点缓存层 | 访问速度快、减轻 DB 压力 | 一致性和过期策略需要设计 |

### 常用读流程（Cache Aside）

1. 先查缓存。
2. 缓存命中直接返回。
3. 缓存未命中查数据库。
4. 回写缓存并返回结果。

## 三、接口稳定性的三件事

1. **参数校验**：避免脏数据进入业务层。
2. **统一异常**：避免前端拿到不一致错误格式。
3. **统一响应**：成功与失败结构一致，便于联调与监控。

## 四、常见误区与修正

### 误区 1：缓存更新“先删缓存再改数据库”

修正：在大多数业务里，更常见实践是“先更新数据库，再删缓存”，减少脏读窗口。

### 误区 2：把 SQL 写在 Controller

修正：Controller 只做输入输出边界，SQL 与业务规则分层管理。

### 误区 3：只关注功能是否可用

修正：还要关注失败路径：缓存不可用怎么办？数据库慢怎么办？

## 五、练习任务

1. 设计一个“查询用户详情”接口，画出三层调用链。
2. 为该接口补一个缓存读流程（命中与未命中都覆盖）。
3. 增加统一异常返回结构（包含错误码和提示信息）。

## 六、错答与修正（章级题解）

### 题目

为什么在后端开发中常用 MySQL + Redis 组合？

### 错答示例（低分）

- “因为 Redis 快，所以都放 Redis 就行。”

### 修正答法（高分）

1. 先定义角色：MySQL 是权威存储，Redis 是性能层。
2. 再说原理：热点读走缓存，降低 DB 压力。
3. 再说边界：缓存失效、一致性、过期策略都需要设计。

### 打分差异

| 维度 | 低分答法 | 高分答法 |
|---|---|---|
| 结构完整 | 单点结论 | 角色 + 原理 + 边界 |
| 工程意识 | 不提失败路径 | 说明一致性与降级 |
| 可落地性 | 没有流程 | 给出读写流程与策略 |

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.util.HashMap;
import java.util.Map;

public class Main {
    // 模拟 MySQL（权威数据源）
    static class UserRepository {
        private final Map<Long, String> mysql = new HashMap<>();

        UserRepository() {
            mysql.put(1L, "alice");
            mysql.put(2L, "bob");
        }

        String findNameById(Long id) {
            return mysql.get(id);
        }
    }

    // 模拟 Redis 缓存
    static class UserCache {
        private final Map<Long, String> redis = new HashMap<>();

        String get(Long id) {
            return redis.get(id);
        }

        void put(Long id, String name) {
            redis.put(id, name);
        }
    }

    // 业务层：实现最小 Cache Aside 读流程
    static class UserService {
        private final UserRepository repository;
        private final UserCache cache;

        UserService(UserRepository repository, UserCache cache) {
            this.repository = repository;
            this.cache = cache;
        }

        String getUserName(Long id) {
            // 1) 先查缓存
            String cached = cache.get(id);
            if (cached != null) {
                return "cache-hit:" + cached;
            }

            // 2) 缓存未命中，查“数据库”
            String fromDb = repository.findNameById(id);
            if (fromDb != null) {
                // 3) 回写缓存，供下一次命中
                cache.put(id, fromDb);
                return "db-hit:" + fromDb;
            }

            // 4) 数据不存在
            return "not-found";
        }
    }

    public static void main(String[] args) {
        UserService service = new UserService(new UserRepository(), new UserCache());

        // 第一次读取：缓存未命中，走“数据库”
        System.out.println(service.getUserName(1L));

        // 第二次读取：命中缓存
        System.out.println(service.getUserName(1L));

        // 读取不存在用户
        System.out.println(service.getUserName(9L));
    }
}
```

**预期输出示例：**

```text
db-hit:alice
cache-hit:alice
not-found
```

## 关联阅读（本仓库）

- [`31-L1-M3-S01-SpringBoot分层开发.md`](./31-L1-M3-S01-SpringBoot分层开发.md)
- [`33-L1-M3-S03-MySQL索引与事务基础.md`](./33-L1-M3-S03-MySQL索引与事务基础.md)
- [`35-L1-M3-S05-缓存旁路模式入门.md`](./35-L1-M3-S05-缓存旁路模式入门.md)
