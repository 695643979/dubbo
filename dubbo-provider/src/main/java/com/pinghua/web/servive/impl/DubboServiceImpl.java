package com.pinghua.web.servive.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinghua.web.service.DubboService;

/**
 * Created by whp on 2019-03-07
 */
@Service(version = "1.0.0")
public class DubboServiceImpl implements DubboService{
    @Override
    public void sayHello(String name) {
        System.out.println(name);
        System.out.println("---dubbo 发布服务----");
    }
}
