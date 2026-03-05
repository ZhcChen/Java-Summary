# 01 Java 语法与面向对象

## 本章目标

- 理解 Java 基础语法背后的设计意图。
- 建立面向对象的职责划分思维。
- 写出可维护、可扩展的基础类结构。

## 一、语法基础不是“会写”，而是“写对”

### 1) 变量与类型

- 优先使用语义明确的变量名，而不是 `a`、`tmp`。
- 明确区分“可变状态”和“不可变状态”。
- 基本类型用于计算，引用类型承载行为与状态。

### 2) 方法设计

- 一个方法尽量只做一件事。
- 方法名描述业务动作，而不是技术动作。
- 入参和返回值要表达边界：允许空值还是不允许。

### 3) 控制流程

- if/else 先处理异常分支，再处理主流程（减少嵌套）。
- 循环中避免复杂副作用，必要时拆分成私有方法。

## 二、面向对象核心：职责、抽象、边界

### 1) 封装

封装不是“把字段 private 就结束了”，核心是：

- 隐藏内部状态变化规则。
- 对外只暴露必要能力。
- 在对象内部保证数据一致性。

### 2) 继承与组合

- 继承适合“is-a”关系，强调语义一致。
- 组合适合能力拼装，灵活性通常更高。
- 实战中优先组合，谨慎深层继承。

### 3) 接口

接口的价值是“约束行为”，不是“凑抽象层”。

- 调用方依赖接口，降低实现替换成本。
- 实现类可以根据场景优化，但不破坏契约。

## 三、常见错误

1. 把业务规则散落在 Controller、Service、Util 多处。
2. 一个类同时负责校验、计算、持久化，导致后期难改。
3. `equals`/`hashCode` 不一致，导致集合行为异常。

## 四、练习任务

1. 设计一个 `Order` 对象，要求能校验状态流转合法性。
2. 用接口定义“支付能力”，实现两个不同支付通道。
3. 识别一段旧代码中的“职责过载类”，尝试拆分。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
interface PaymentService {
    // 接口定义“做什么”，不定义“怎么做”
    boolean pay(int amount);
}

class WechatPaymentService implements PaymentService {
    @Override
    public boolean pay(int amount) {
        // 真实场景会走外部网关，这里只保留最小演示逻辑
        return amount > 0;
    }
}

class Order {
    private final long orderId;
    private int amount;
    private String status;

    Order(long orderId, int amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = "CREATED";
    }

    // 封装状态变化规则：只允许 CREATED -> PAID
    boolean markPaid(PaymentService paymentService) {
        if (!"CREATED".equals(status)) {
            return false;
        }
        boolean success = paymentService.pay(amount);
        if (success) {
            status = "PAID";
        }
        return success;
    }

    String getStatus() {
        return status;
    }

    long getOrderId() {
        return orderId;
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order(1001L, 88);
        PaymentService paymentService = new WechatPaymentService();

        System.out.println("before=" + order.getStatus());
        boolean paid = order.markPaid(paymentService);
        System.out.println("paid=" + paid + ", after=" + order.getStatus());
    }
}
```

**预期输出示例：**

```text
before=CREATED
paid=true, after=PAID
```

## 返回

- [`README.md`](./README.md)
