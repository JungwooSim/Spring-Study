package me.study.batch.dto

// CSV 데이터를 받을 객체
data class CustomerDto(
  var name: String = "",
  var email: String = "",
  var age: Int = 0
)