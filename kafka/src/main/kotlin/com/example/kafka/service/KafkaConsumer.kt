package com.example.kafka.service

import com.example.kafka.dto.DataDto
import org.apache.kafka.clients.consumer.Consumer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class KafkaConsumer {

  @KafkaListener(
    topics = ["hello.kafka"],
    groupId = "foo",
    containerFactory = "helloKafkaListenerContainerFactory"
  )
  fun helloListener(dataDto: DataDto, consumer: Consumer<String, String>) {
    println("----")
    println(dataDto.name)
    println(dataDto.age)

    consumer.commitSync()
  }
}