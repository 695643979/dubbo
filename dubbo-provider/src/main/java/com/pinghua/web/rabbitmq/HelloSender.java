package com.pinghua.web.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/12/6.
 * @author: wenhuaping
 */
@Slf4j
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(HelloRequestVo sendOriginData) {
        String origin = "";
        try {
            origin = JSON.toJSONString(sendOriginData);
            log.info("begin: MQ发送的数据：{}", origin);
//              origin = "ni men hao ！";
//            log.info("begin: MQ发送的数据：{}", origin);
            Message message = MessageBuilder
                    .withBody(origin.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();

//            //MQ反应速度快，先数据库，再mq，最后事务
//            rabbitTemplate.convertAndSend(RabbitConfig.helloExchange, RabbitConfig.helloPingQueue, message);
//            log.info("rabbitMq发送结束！");
//            log.info("begin: MQ发送的数据：{}", message);
            this.rabbitTemplate.convertAndSend(RabbitConfig.helloPingQueue, message);

        } catch (Exception e) {
            log.error("MQ发送的数据异常：{}，异常信息为{}", origin, e);
        }
    }
}
