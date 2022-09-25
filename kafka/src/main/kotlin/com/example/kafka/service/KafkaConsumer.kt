package com.example.kafka.service

import com.example.kafka.dto.DataDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class KafkaConsumer {

  @KafkaListener(
    topics = ["hello.kafka"],
    groupId = "foo",
    containerFactory = "helloKafkaListenerContainerFactory"
  )
  fun helloListener(dataDto: DataDto) {
    println("----")
    println(dataDto.name)
    println(dataDto.age)
  }
}