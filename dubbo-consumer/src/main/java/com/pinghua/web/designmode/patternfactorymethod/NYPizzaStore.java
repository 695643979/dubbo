package com.pinghua.web.designmode.patternfactorymethod;

/**
 * 具体工厂。披萨分店。NYPizzaStore.java
 * Created by wenhuaping on 2019/4/9.
 * @author: wenhuaping
 */
public class NYPizzaStore extends PizzaStore{

    @Override
    Pizza createPizza(String type) {
        Pizza pizza = null;
        if("cheese".equals(type)){
            pizza = new NYStyleCheesePizza();
        }else if("veggie".equals(type)){
           // pizza = new NYStyleVeggiePizza();
        }else if("clam".equals(type)){
            //pizza = new NYStyleClamPizza();
        }else if("pepperoni".equals(type)){
            //pizza = new NYStylePepperoniPizza();
        }

        return pizza;
    }
}
