package com.pinghua.web.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 声明所有的测试队列
 * @author wenhuaping
 */
@Slf4j
@Configuration
public class RabbitConfig {

    /** 队列名 */
    public static final String helloPingQueue ="hello.Ping.Queue";

    /** 交换机名 */
    public static final String helloExchange ="hello.Ping.EXCHANGE";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryPlus(
            SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        rabbitListenerContainerFactory.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitListenerContainerFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper xssObjectMapper) {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定义队列
     * @return
     */
    @Bean
    public Queue helloPingQueue() {
        return new Queue(helloPingQueue);
    }

    /**
     * 定义交换机
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(helloExchange);
    }

    /**
     * 把队列与交换机绑定在一起
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(TopicExchange exchange) {
        return BindingBuilder.bind(helloPingQueue()).to(exchange).with(helloPingQueue);
    }
}
