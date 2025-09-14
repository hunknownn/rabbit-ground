package com.example.rabbitmq.service

import com.example.rabbitmq.config.RabbitConfig
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MessageConsumer {
    private val logger = LoggerFactory.getLogger(MessageConsumer::class.java)

    @RabbitListener(queues = [RabbitConfig.QUEUE_NAME])
    fun receiveMessage(message: String) {
        logger.info("Received message: $message")
        // 메시지 처리 로직
        processMessage(message)
    }

    private fun processMessage(message: String) {
        // 실제 비즈니스 로직 구현
        logger.info("Processing message: $message")
    }
}
