package com.pinghua.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinghua.web.service.DubboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by whp on 2019-03-07
 */
@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @Reference(version = "1.0.0")
    DubboService dubboService;

    @GetMapping("/sayHello")
    public String sayHello() {
        System.out.println("dubbo test begin ...");
        dubboService.sayHello("consumer request service!");
        return "dubbo test return! ";
    }


}
