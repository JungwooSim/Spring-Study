package com.example.kafka.dto

data class Stock(
  val date: String,
  val open: Double,
  val high: Double,
  val close: Double,
  val average: Double,
  val volume: Long,
)