package com.example.webclient_kotlin.controller

import com.example.webclient_kotlin.service.RedisCacheService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisCacheController(
  private val redisCacheService: RedisCacheService,
) {

  @GetMapping("/redis")
  fun cacheTest(): String {
    redisCacheService.cacheTest("cache:")
    return "OK"
  }
}