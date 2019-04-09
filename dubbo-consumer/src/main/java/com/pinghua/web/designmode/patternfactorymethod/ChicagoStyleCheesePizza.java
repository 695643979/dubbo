package com.pinghua.web.designmode.patternfactorymethod;

/**
 * 具体产品类：ChicagoStyleCheesePizza.java
 * Created by wenhuaping on 2019/4/9.
 * @author: wenhuaping
 */
public class ChicagoStyleCheesePizza extends Pizza {

    public ChicagoStyleCheesePizza(){
        name = "Chicago Style Deep Dish Cheese Pizza";
        dough = "Extra Thick Crust Dough";
        sause = "Plum Tomato Sauce";

        toppings.add("Shredded Mozzarella Cheese");
    }

    @Override
    public void cut(){
        System.out.println("Cutting the Pizza into square slices");
    }

}
