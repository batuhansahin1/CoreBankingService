package com.walletProject.coreBankingService.core.utilities.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	@Value("${rabbitmq.exchange.transfer}")
    private String transferExchange;

    @Value("${rabbitmq.queue.transfer-created}")
    private String transferCreatedQueue;

    @Value("${rabbitmq.routing.key.transfer-created}")
    private String transferCreatedRoutingKey;
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
	// 1. Santrali (Exchange) Tanımla
    @Bean
    public TopicExchange transferExchange() {
        return new TopicExchange(transferExchange);
    }

    // 2. Kuyruğu (Queue) Tanımla
    @Bean
    public Queue transferCreatedQueue() {
        return new Queue(transferCreatedQueue, true); // true = Sunucu kapansa bile kuyruk silinmez (Durable)
    }

    // 3. Kuyruğu ve Santrali Routing Key ile Birbirine Bağla
    @Bean
    public Binding transferCreatedBinding(Queue transferCreatedQueue, TopicExchange transferExchange) {
        return BindingBuilder
                .bind(transferCreatedQueue)
                .to(transferExchange)
                .with(transferCreatedRoutingKey);
    }
}
