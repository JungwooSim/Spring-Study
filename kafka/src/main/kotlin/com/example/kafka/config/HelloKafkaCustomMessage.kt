package com.example.kafka.config

import com.example.kafka.dto.DataDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.stereotype.Component

@Configuration
class HelloKafkaCustomMessage {

  val bootstrapAddress: String = "localhost:9092"

  @Bean
  fun helloKafkaConsumerFactory(): ConsumerFactory<String, DataDto> {
    val props = mapOf<String, Any>(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
      ConsumerConfig.GROUP_ID_CONFIG to "groupId",
    )

    return DefaultKafkaConsumerFactory(
      props,
      StringDeserializer(),
      JsonDeserializer(DataDto::class.java)
    )
  }

  @Bean
  fun helloKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, DataDto> {
    val factory = ConcurrentKafkaListenerContainerFactory<String, DataDto>()
    factory.consumerFactory = helloKafkaConsumerFactory()
    return factory
  }
}