package com.pinghua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * Created by whp on 2019-03-07
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProviderApplication {
    public static void main(String [] args){
        SpringApplication.run(ProviderApplication.class,args);
    }
}
