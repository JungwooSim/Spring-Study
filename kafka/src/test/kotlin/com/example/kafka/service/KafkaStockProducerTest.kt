package com.example.kafka.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KafkaStockProducerTest(
  @Autowired private val kafkaStockProducer: KafkaStockProducer,
) {

  @Test
  fun readStockFileTest() {
    kafkaStockProducer.readStockFile()
  }

}