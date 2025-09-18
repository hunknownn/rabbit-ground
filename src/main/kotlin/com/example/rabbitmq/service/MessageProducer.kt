package com.example.rabbitmq.service

import com.example.rabbitmq.config.RabbitConfig
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import java.lang.IllegalStateException

@Service
class MessageProducer(
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = LoggerFactory.getLogger(MessageProducer::class.java)

    fun sendMessage(message: Any) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
            )
            logger.info("Message sent successfully: $message")
        } catch (e: Exception) {
            logger.error("Failed to send message: $message", e)
            throw e
        }
    }

    fun senMessageForFail() {
        val message = mapOf(
            "type" to "#dlq#",
            "message" to "Connection test",
            "timestamp" to System.currentTimeMillis()
        )

        try {
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
            )
            logger.info("Message sent successfully: $message")
        } catch (e: Exception) {
            logger.error("Failed to send message: $message", e)
            throw e
        }
    }
}
