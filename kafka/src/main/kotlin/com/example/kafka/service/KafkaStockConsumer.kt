package com.example.kafka.service

import com.example.kafka.dto.Stock
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaStockConsumer {

  @KafkaListener(
    topics = ["stock"],
    groupId = "stock-3",
  )
  fun stockListener(consumer: ConsumerRecord<String, Stock>) {
    println("-------- consumer stock --------")
    println(consumer.value())
  }
}