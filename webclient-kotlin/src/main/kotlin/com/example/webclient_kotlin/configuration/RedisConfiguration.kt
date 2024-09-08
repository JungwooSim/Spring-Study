package com.example.webclient_kotlin.configuration

import io.lettuce.core.ClientOptions
import java.time.Duration
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration(
  private val redisProperties: RedisProperties,
) {

  @Bean
  fun redisConnectionFactory(): RedisConnectionFactory {
    with(redisProperties) {
      val clientConfiguration = LettuceClientConfiguration.builder()
        .clientOptions(
          ClientOptions.builder()
            .autoReconnect(true)
            .build()
        )
        .commandTimeout(Duration.ofMinutes(3))
        .build()

      return LettuceConnectionFactory(RedisStandaloneConfiguration(host, port), clientConfiguration)
    }
  }

  @Bean
  fun redisTemplate(): RedisTemplate<String, String> {
    return with(RedisTemplate<String, String>()) {
      connectionFactory = redisConnectionFactory()
      keySerializer = StringRedisSerializer()
      valueSerializer = StringRedisSerializer()
      this
    }
  }
}