# 01 Java 语法与面向对象

## 本章目标

- 从“会写语法”提升到“能用语法表达业务意图”。
- 理解面向对象设计的核心：职责、边界、协作。
- 能把业务规则封装进对象，而不是散落在流程代码里。

## 前置知识

- 会运行 `main` 方法。
- 理解变量、分支、循环、方法。
- 知道类与对象的基本概念。

## 一、先建立正确心智模型

### 1) 语法不是目的，表达才是目的

同样能跑通的代码，维护成本可能相差十倍。基础语法阶段最重要的不是“写出结果”，而是做到：

- 变量名能表达业务含义，而不是临时缩写。
- 方法职责单一，调用链可追踪。
- 条件分支以业务规则组织，不是随意拼接 `if-else`。

### 2) 面向对象的核心是“让变化可控”

判断类设计是否健康，优先问三个问题：

1. 这个类只负责一类变化吗？
2. 关键状态是否只能通过业务动作变更？
3. 对外暴露的 API 是否最小且稳定？

如果一个类同时负责参数校验、流程编排、数据持久化，后续任何需求变动都会牵连整类代码。

### 3) 接口是契约，不是装饰

接口真正的价值是“替换实现而不影响调用方”：

- 业务层依赖接口，降低耦合。
- 新增渠道时新增实现类，而不是改老流程。
- 测试时可替换成 stub/mock 实现。

## 二、从语法到建模：一个订单支付案例

我们用“订单支付”这个高频场景，把几个关键点串起来：

- **封装**：订单状态不允许外部随意改。
- **抽象**：支付能力用接口定义。
- **扩展**：新增支付渠道不改订单核心逻辑。
- **边界**：非法状态流转必须失败并可解释。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
import java.util.Objects;

interface PaymentService {
    // 返回 true 表示支付网关受理成功
    boolean pay(long orderId, int amountFen);

    // 便于日志定位：当前实现名称
    String channelName();
}

class WechatPaymentService implements PaymentService {
    @Override
    public boolean pay(long orderId, int amountFen) {
        // 示例中仅模拟最小规则：金额大于 0 才认为受理成功
        return amountFen > 0;
    }

    @Override
    public String channelName() {
        return "WECHAT";
    }
}

class AlipayPaymentService implements PaymentService {
    @Override
    public boolean pay(long orderId, int amountFen) {
        // 演示“实现可替换”：这里做了另一条校验逻辑
        return orderId > 0 && amountFen >= 100;
    }

    @Override
    public String channelName() {
        return "ALIPAY";
    }
}

class Order {
    enum Status {
        CREATED,
        PAID,
        CLOSED
    }

    private final long orderId;
    private final int amountFen;
    private Status status;

    Order(long orderId, int amountFen) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("orderId must be positive");
        }
        if (amountFen <= 0) {
            throw new IllegalArgumentException("amountFen must be positive");
        }
        this.orderId = orderId;
        this.amountFen = amountFen;
        this.status = Status.CREATED;
    }

    boolean markPaid(PaymentService paymentService) {
        Objects.requireNonNull(paymentService, "paymentService must not be null");

        // 只允许 CREATED -> PAID，避免重复支付
        if (status != Status.CREATED) {
            System.out.println("order=" + orderId + " reject pay: status=" + status);
            return false;
        }

        boolean paid = paymentService.pay(orderId, amountFen);
        if (paid) {
            status = Status.PAID;
            System.out.println("order=" + orderId + " paid by " + paymentService.channelName());
        }
        return paid;
    }

    boolean close() {
        // 业务规则：已支付订单允许关闭
        if (status == Status.PAID) {
            status = Status.CLOSED;
            return true;
        }
        return false;
    }

    Status getStatus() {
        return status;
    }

    long getOrderId() {
        return orderId;
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order(1001L, 8800);
        System.out.println("before status=" + order.getStatus());

        // 调用方只依赖 PaymentService 接口，不关心具体实现细节
        PaymentService payment = new WechatPaymentService();
        boolean firstPaid = order.markPaid(payment);

        // 再次支付会失败，验证状态机边界
        boolean secondPaid = order.markPaid(new AlipayPaymentService());
        boolean closed = order.close();

        System.out.println("orderId=" + order.getOrderId());
        System.out.println("firstPaid=" + firstPaid + ", secondPaid=" + secondPaid);
        System.out.println("closed=" + closed + ", finalStatus=" + order.getStatus());
    }
}
```

**预期输出示例：**

```text
before status=CREATED
order=1001 paid by WECHAT
order=1001 reject pay: status=PAID
orderId=1001
firstPaid=true, secondPaid=false
closed=true, finalStatus=CLOSED
```

## 三、常见误区与修正

1. **误区：面向对象就是多写几个类**  
   修正：关键是“职责隔离 + 规则封装 + 可替换扩展”。

2. **误区：提供 setter 就是灵活**  
   修正：核心状态应通过业务动作改变，避免绕过规则。

3. **误区：接口越多越专业**  
   修正：有变化点再抽接口；没有变化点先保持简单。

## 四、面试高频问答（基础层）

### Q1：什么是封装？为什么不能随便开放字段？

- 一句话结论：封装是把状态与规则放在一起，防止外部绕过业务约束。
- 关键点：
  1. 外部直接改字段会破坏状态一致性。
  2. 对象暴露“动作”比暴露“状态修改器”更安全。
  3. 封装降低了非法状态传播范围。

### Q2：接口和抽象类怎么选？

- 一句话结论：优先接口定义能力边界，抽象类用于复用稳定实现。
- 关键点：
  1. 接口强调“能做什么”。
  2. 抽象类强调“已有公共实现”。
  3. 如果行为变化频繁，优先接口 + 组合。

## 练习与自测

1. 在 `Order` 中增加 `cancel()`，要求只允许 `CREATED -> CLOSED`。
2. 新增 `MockPaymentService`，让支付固定失败，用于单元测试演练。
3. 用 3 分钟口述：为什么示例代码比“直接改订单状态字段”更可维护。

## 本章小结

- 语法的高级用法不是炫技，而是表达清晰。
- 面向对象的价值是把变化收敛到可控边界里。
- 接口让扩展发生在新增代码，而不是改动旧代码。

## 下一章

- [`02-集合与泛型.md`](./02-集合与泛型.md)
