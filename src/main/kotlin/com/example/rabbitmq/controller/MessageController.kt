package com.example.rabbitmq.controller

import com.example.rabbitmq.service.MessageProducer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessageController(
    private val messageProducer: MessageProducer
) {
    @GetMapping("/fail")
    fun testDlq(): ResponseEntity<String> {
        return try {
            messageProducer.senMessageForFail()
            ResponseEntity.ok("RabbitMQ connection test successful")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("Consume by DLQ: ${e.message}")
        }
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: Map<String, Any>): ResponseEntity<String> {
        return try {
            messageProducer.sendMessage(message)
            ResponseEntity.ok("Message sent successfully")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("Failed to send message: ${e.message}")
        }
    }

    @GetMapping("/test")
    fun testConnection(): ResponseEntity<String> {
        return try {
            val testMessage = mapOf(
                "type" to "test",
                "message" to "Connection test",
                "timestamp" to System.currentTimeMillis()
            )
            messageProducer.sendMessage(testMessage)
            ResponseEntity.ok("RabbitMQ connection test successful")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("RabbitMQ connection failed: ${e.message}")
        }
    }
}
