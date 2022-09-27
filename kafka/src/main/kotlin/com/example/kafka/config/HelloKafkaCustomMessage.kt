package com.example.kafka.config

import com.example.kafka.dto.DataDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class HelloKafkaCustomMessage {

  val bootstrapAddress: String = "localhost:9092"

  @Bean
  fun helloKafkaProducerFactory(): ProducerFactory<String, DataDto> {
    val props = mutableMapOf<String, Any>()

    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

    return DefaultKafkaProducerFactory(props)
  }

  @Bean
  fun helloKafkaTemplate(): KafkaTemplate<String, DataDto> {
    return KafkaTemplate(helloKafkaProducerFactory())
  }

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
    factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
    return factory
  }
}