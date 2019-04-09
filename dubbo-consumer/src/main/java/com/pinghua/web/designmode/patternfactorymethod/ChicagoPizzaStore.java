package com.pinghua.web.designmode.patternfactorymethod;

/**
 * Created by wenhuaping on 2019/4/9.
 * @author: wenhuaping
 */
public class ChicagoPizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String type) {
        Pizza pizza = null;
        if("cheese".equals(type)){
            pizza = new ChicagoStyleCheesePizza();
        } else if("clam".equals(type)){
            //pizza = new ChicagoStyleClamPizza();
        }else if("pepperoni".equals(type)) {
           // pizza = new ChicagoStylePepperoniPizza();
        }else if("veggie".equals(type)){
           // pizza = new ChicagoStyleVeggiePizza();
        }

        return pizza;
    }
}
