package com.aliatic.core.trm.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_TRM = "MQ_QA_TRM";
    public static final String QUEUE_DTF = "MQ_QA_DTF";
    public static final String EXCHANGE = "MQ_QA_TRM";
    public static final String ROUTING_KEY_TRM = "MQ_QA_TRM";
    public static final String ROUTING_KEY_DTF = "MQ_QA_DTF";

    @Bean
    Queue queueTRM() {
        return new Queue(QUEUE_TRM);
    }

    @Bean
    Queue queueDTF() {
        return new Queue(QUEUE_DTF);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding bindingTRM(Queue queueTRM, TopicExchange exchange) {
        return BindingBuilder.bind(queueTRM).to(exchange).with(ROUTING_KEY_TRM);
    }

    @Bean
    Binding bindingDTF(Queue queueDTF, TopicExchange exchange) {
        return BindingBuilder.bind(queueDTF).to(exchange).with(ROUTING_KEY_DTF);
    }

    @Bean
    MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
