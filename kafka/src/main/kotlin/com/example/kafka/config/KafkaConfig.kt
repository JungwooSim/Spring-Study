package com.example.kafka.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaAdmin

@EnableKafka
@Configuration
class KafkaConfig {

  val bootstrapAddress: String = "localhost:29092"

  @Bean
  fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092"))

  @Bean
  fun topics() = KafkaAdmin.NewTopics(
    TopicBuilder.name("hello.kafka")
      .replicas(1)
      .partitions(1)
      .build(),
  )

  @Bean
  fun consumerFactory(): ConsumerFactory<String, String> {
    val props = mapOf<String, Any>(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
      ConsumerConfig.GROUP_ID_CONFIG to "groupId",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
    )

    return DefaultKafkaConsumerFactory(props)
  }

  @Bean
  fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
    factory.consumerFactory = consumerFactory()
    return factory
  }

}