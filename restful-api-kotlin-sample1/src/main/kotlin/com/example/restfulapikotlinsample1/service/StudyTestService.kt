package com.example.restfulapikotlinsample1.service

import com.example.restfulapikotlinsample1.model.Customer
import com.example.restfulapikotlinsample1.persistence.CustomerRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudyTestService(
  private val redisTemplate: ReactiveRedisTemplate<String, String>,
  private val customerRepository: CustomerRepository,
) {

  @Transactional
  fun redisSetTransactionTest() {
    redisTemplate.opsForValue().set("TEST", "OKOK").block()
  }

  @Transactional
  fun redisSetErrorTransactionTest() {
    redisTemplate.opsForValue().set("TEST", "OKOK").block()

    throw RuntimeException("ERROR!!")
  }

  @Transactional
  fun redisGetTransactionTest() {
    println("TEST : ${redisTemplate.opsForValue().get("TEST").block()}")
  }

  @Transactional
  fun redisDeleteTransactionTest() {
    redisTemplate.opsForValue().delete("TEST").block()
  }

  @Transactional
  fun h2SetErrorTransactionTest() {
    customerRepository.save(
      Customer(
        firstName = "BIG"
      )
    )

    throw RuntimeException("ERROR!!")
  }

  @Transactional
  fun h2GetTransactionTest() {
    println("COUNT : ${customerRepository.count()}")
    customerRepository.findAll().forEach {
      println("ID : ${it.firstName}")
    }
  }
}