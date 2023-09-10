package com.example.kafka.service

import com.example.kafka.dto.DataDto
import jakarta.annotation.PostConstruct
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
  private val helloKafkaTemplate: KafkaTemplate<String, DataDto>
) {

  @PostConstruct
  fun postConstruct() {
    this.sendMessage()
  }

  fun sendMessage() {
    val dataDto = DataDto(name = "big", age = 10)

    helloKafkaTemplate.send("hello.kafka", dataDto)
  }
}