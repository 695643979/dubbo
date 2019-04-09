package com.pinghua.web.designmode.patternfactorymethod;

/**
 * 抽象工厂：披萨总店。PizzaStore.java
 * Created by wenhuaping on 2019/4/9.
 * @author: wenhuaping
 */
public abstract class PizzaStore {

    public Pizza orderPizza(String type){
        Pizza pizza;
        pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    abstract Pizza createPizza(String type);

}
