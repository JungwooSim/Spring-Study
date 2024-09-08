package com.example.webclient_kotlin.dto

import java.time.LocalDateTime

data class RedisCacheDto(
  val name: String = "홍길동",
  val age: Int = 10,
  val age2: Long = 10,
  val family: ArrayList<String> = arrayListOf("홍길동", "홍길동"),
  val time: LocalDateTime = LocalDateTime.now(),
)