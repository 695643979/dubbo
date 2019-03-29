package com.pinghua.web.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Administrator on 2018/12/7.
 * @author: wenhuaping
 */
@Slf4j
@Controller
@RequestMapping("/helloRabbitMq")
public class RabbitMqProviderController {

    @Autowired
    private HelloSender helloSender;

    /**
     * rabbitMq 测试类
     * rabbitmq后台管理： http://127.0.0.1:15672/#/     账号： xxxx    密码：xxxx2018
     * 访问路径：http://10.10.20.192:7001/helloRabbitMq/testRabbitMq
     */
    @GetMapping("/testRabbitMq")
    @ResponseBody
    public void testRabbitMq() {
        HelloRequestVo vo = new HelloRequestVo();
        vo.setId(27069363035185152L);
        vo.setAge(18);
        vo.setAccount("13824797543");
        vo.setName("华哥");
        helloSender.send(vo);
    }

}
