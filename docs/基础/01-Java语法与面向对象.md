# 01 Java 语法与面向对象

## 本章目标

- 建立 Java 基础语法的工程化使用习惯。
- 理解面向对象中“职责、抽象、边界”的核心思想。
- 能写出可维护的基础类与接口。

## 前置知识

- 会写最小可运行的 `main` 方法。
- 理解变量、条件语句、循环和方法调用。
- 知道类与对象的基本概念。

## 核心概念

1. **语法是表达工具**：重点是把业务意图写清楚。
2. **面向对象是建模方式**：类不是越多越好，而是职责清楚。
3. **接口是契约**：调用方依赖行为约束，而非具体实现。

## 原理展开

- 变量命名和方法命名直接决定代码可读性。
- 封装的核心是“把状态变化规则留在对象内部”，避免外部任意破坏对象状态。
- 组合优先于继承：继承耦合更强，组合更容易替换实现。

## 典型场景

- 订单支付：订单对象管理状态流转，支付接口抽象不同支付通道。
- 用户资料更新：通过对象方法统一校验规则，而不是散落在多个工具类。

## 常见误区

1. 把“会写 class”等同于“会 OOP”。
2. 一个类同时处理控制流程、业务计算、持久化，导致职责混乱。
3. 接口只是“为了有接口而有接口”，没有稳定契约边界。

## Java 示例代码（含注释，可直接运行）

**建议文件名：** `Main.java`  
**运行命令：** `javac Main.java && java Main`

```java
interface PaymentService {
    // 只定义能力，不关心具体支付实现
    boolean pay(int amount);
}

class WechatPaymentService implements PaymentService {
    @Override
    public boolean pay(int amount) {
        // 真实场景会调用支付网关，这里仅做最小演示
        return amount > 0;
    }
}

class Order {
    private final long orderId;
    private final int amount;
    private String status;

    Order(long orderId, int amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = "CREATED";
    }

    // 将状态变更规则封装在对象内部，避免外部随意修改
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
        System.out.println("orderId=" + order.getOrderId() + ", paid=" + paid + ", after=" + order.getStatus());
    }
}
```

**预期输出示例：**

```text
before=CREATED
orderId=1001, paid=true, after=PAID
```

## 练习与自测

1. 设计 `CouponService` 接口，新增“满减券”和“折扣券”两种实现。
2. 把订单状态扩展为 `CREATED -> PAID -> CLOSED`，补齐状态校验。
3. 自测：是否能解释“为什么用接口，而不是直接 new 实现类”。

## 本章小结

- 语法写得清楚，是后续工程能力的基础。
- OOP 的核心价值是职责划分和变化隔离。

## 下一章

- [`02-集合与泛型.md`](./02-集合与泛型.md)
