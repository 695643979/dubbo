package com.pinghua.web.designmode.patternfactorymethod;

/**
 * 引用：http://www.iocoder.cn/DesignPattern/xiaomingge/Factory-Method/
 * 一、问题
 * 在前一章《设计模式读书笔记—–简单工厂模式》中通过披萨的实例介绍了简单工厂模式。在披萨实例中，如果我想根据地域的不同生产出不同口味的披萨，如纽约口味披萨，芝加哥口味披萨。如果利用简单工厂模式，我们需要两个不同的工厂，NYPizzaFactory、ChicagoPizzaFactory。在该地域中有很多的披萨店，他们并不想依照总店的制作流程来生成披萨，而是希望采用他们自己的制作流程。这个时候如果还使用简单工厂模式，因为简单工厂模式是将披萨的制作流程完全承包了。那么怎么办？
 *
 * 二、解决方案
 * 我们可以这样解决：将披萨的制作方法交给各个披萨店完成，但是他们只能提供制作完成的披萨，披萨的订单处理仍然要交给披萨工厂去做。也就是说，我们将createPizza()方法放回到PizzaStore中，其他的部分还是保持不变。
 *
 * 三、基本定义
 * 工厂方法模式定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个。工厂方法模式让实例化推迟到子类。
 *
 * 四、模式结构
 * 工厂方法模式的UML结构图：
 *
 *
 * Product：抽象产品。所有的产品必须实现这个共同的接口，这样一来，使用这些产品的类既可以引用这个接口。而不是具体类。
 *
 * ConcreteProduct：具体产品。
 *
 * Creator：抽象工厂。它实现了所有操纵产品的方法，但不实现工厂方法。Creator所有的子类都必须要实现factoryMethod()方法。
 *
 * ConcreteCreator：具体工厂。制造产品的实际工厂。它负责创建一个或者多个具体产品，只有ConcreteCreator类知道如何创建这些产品。
 *
 * 工厂方法模式是简单工厂模式的延伸。在工厂方法模式中，核心工厂类不在负责产品的创建，而是将具体的创建工作交给子类去完成。也就是后所这个核心工厂仅仅只是提供创建的接口，
 * 具体实现方法交给继承它的子类去完成。当我们的系统需要增加其他新的对象时，我们只需要添加一个具体的产品和它的创建工厂即可，不需要对原工厂进行任何修改，这样很好地符合了“开闭原则”。
 *
 * Created by wenhuaping on 2019/4/9.
 * @author: wenhuaping
 */
public class PizzaTestDriveTest {

    public static void main(String[] args) {
        System.out.println("---------Joel 需要的芝加哥的深盘披萨---------");
        ChicagoPizzaStore chicagoPizzaStore = new ChicagoPizzaStore();       //建立芝加哥的披萨店
        Pizza joelPizza =chicagoPizzaStore.orderPizza("cheese");             //下订单
        System.out.println("Joel ordered a " + joelPizza.getName() + "\n");

        System.out.println("---------Ethan 需要的纽约风味的披萨---------");
        NYPizzaStore nyPizzaStore = new NYPizzaStore();
        Pizza ethanPizza = nyPizzaStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + ethanPizza.getName() + "\n");
    }

}
