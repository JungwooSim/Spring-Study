package com.example.restfulapikotlinsample1.service

import java.lang.Exception
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StudyTestServiceTest(
  @Autowired
  private val studyTestService: StudyTestService
) {

  @Test
  fun redisTransactionTest() {
//    studyTestService.redisSetTransactionTest()
//
//    studyTestService.redisGetTransactionTest()

    studyTestService.redisDeleteTransactionTest()

//    studyTestService.redisGetTransactionTest()

    try {
      studyTestService.redisSetErrorTransactionTest()
    } catch (ex: Exception) {
      println("ERROR!!")
    }
    studyTestService.redisGetTransactionTest()
  }

  @Test
  fun h2TransactionTest() {
    try {
      studyTestService.h2SetErrorTransactionTest()
    } catch (ex: Exception) {
      println("ERROR!!")
    }

    studyTestService.h2GetTransactionTest()
  }
}