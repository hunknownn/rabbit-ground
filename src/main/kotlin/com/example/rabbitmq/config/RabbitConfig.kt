package com.example.rabbitmq.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    companion object {
        const val EXCHANGE_NAME = "test.exchange"
        const val QUEUE_NAME = "test.queue"
        const val ROUTING_KEY = "test.routing.key"
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun queue(): Queue {
        return QueueBuilder.durable(QUEUE_NAME).build()
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with(ROUTING_KEY)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = Jackson2JsonMessageConverter()
        return template
    }
}
