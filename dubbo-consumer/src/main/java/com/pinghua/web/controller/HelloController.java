package com.pinghua.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinghua.web.service.DubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wenhuaping on 2019/4/4.
 * @author: wenhuaping
 */
@Api(value = "后台管理", tags = "swagger测试类")
@RestController
@RequestMapping("/hello")
public class HelloController {


    @Reference(version = "1.0.0")
    DubboService dubboService;


    @ApiOperation("sayHello方法")
    @ApiImplicitParam(paramType="query", name = "param", value = "入参", required = true, dataType = "String")
    @PostMapping("/sayHello")
    public String sayHello(@Validated @NotBlank(message = "入参param不能为空")@RequestParam("param") String param) {
        System.out.println("dubbo test begin ...");
        System.out.println("入参：" + param);
        dubboService.sayHello("consumer request service!");
        return "dubbo test consumer return! ";
    }

}
