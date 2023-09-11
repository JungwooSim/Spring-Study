package com.example.kafka.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class KafkaStockProducer {

  @PostConstruct
  fun postConstruct() {
    println("KafkaStockProducer")
  }
}