package com.pinghua.web.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * Created by wenhuaping on 2018/12/7.
 * @author: wenhuaping
 */
@Slf4j
@Component
public class RabbitMqConsumerController {

    /**
     * 接收对象数据
     * @param sendOriginData
     */
    @RabbitHandler
    @RabbitListener(queues = {RabbitConfig.helloPingQueue})
    void process(HelloRequestVo sendOriginData) {
        try {
            log.info("消费者消费begin:");
            // HelloRequestVo: HelloRequestVo(id=27069363035185152, name=华哥, age=18, account=13824797543)
            log.info("HelloRequestVo: " + sendOriginData);
//            log.info("消费者 Receiver 接收的消息: " + JsonUtil.objectToJsonString(hello));
            log.info("消费者消费end:");
        } catch (Exception e) {
            log.error("消费者 Receiver 接收消息异常！");
        }
    }

}
