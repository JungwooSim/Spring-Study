package com.example.webclient_kotlin.service

import com.example.webclient_kotlin.dto.RedisCacheDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RedisCacheService {

  @Cacheable(
    cacheNames = ["10SecondTTLCache"],
    key = "#text",
  )
  fun cacheTest(
    text: String
  ): RedisCacheDto {
    return RedisCacheDto()
  }
}
