package com.example.kafka.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class KafkaConsumer {

  @KafkaListener(topics = ["hello.kafka"], groupId = "foo")
  fun helloListener(message: String) {
    println("----")
    println(message)
  }
}