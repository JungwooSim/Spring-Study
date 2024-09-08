package com.example.webclient_kotlin.configuration

import com.example.webclient_kotlin.enums.CacheConfigEnum
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class CacheConfiguration(
  private val connectionFactory: RedisConnectionFactory,
) : CachingConfigurer {

  @Bean
  override fun cacheManager(): CacheManager {
    return RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
      .cacheDefaults(defaultCacheConfiguration())
      .withInitialCacheConfigurations(initialCacheConfigurations())
      .build()
  }

  fun defaultCacheConfiguration(): RedisCacheConfiguration {
    return RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(CacheConfigEnum.ONE_MINUTE_TTL_CACHE.ttl)
      .computePrefixWith { "" }
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
        GenericJackson2JsonRedisSerializer(cacheObjectMapper)
      ))
  }

  private val cacheObjectMapper = with(ObjectMapper()) {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    activateDefaultTyping(
      BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType(Any::class.java)
        .build(),
      ObjectMapper.DefaultTyping.EVERYTHING
    )
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    registerModules(JavaTimeModule(), KotlinModule.Builder().build())
  }

  fun initialCacheConfigurations(): Map<String, RedisCacheConfiguration> {
    return CacheConfigEnum.entries.associate {
      it.cacheName to defaultCacheConfiguration().entryTtl(it.ttl)
    }
  }
}