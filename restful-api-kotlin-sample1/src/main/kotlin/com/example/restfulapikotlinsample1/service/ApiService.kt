package com.example.restfulapikotlinsample1.service

import java.util.concurrent.ExecutionException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig {
 @Bean
 fun webClientBuilder(): WebClient.Builder {
  return WebClient.builder()
 }
}

@Service
class ApiService(
  private val webClientBuilder: WebClient.Builder
) {

  @Async
  fun callApi(url: String?): ListenableFuture<String> {
    return AsyncResult(
      webClientBuilder.build().get().uri(url!!).retrieve().bodyToMono(
        String::class.java
      ).block()
    )
  }

  @Throws(ExecutionException::class, InterruptedException::class)
  fun a(): String {
    val url1 = "https://www.naver.com/"
    val url2 = "https://www.naver.com/"

    // 비동기 API 호출
    val response1 = callApi(url1)
    val response2 = callApi(url2)

    // 결과가 완료될 때까지 대기
    val result1 = response1.get()
    val result2 = response2.get()

    // 결과 비교 후 반환
    return compareResults(result1, result2)
  }

  private fun compareResults(result1: String, result2: String): String {
    // 결과를 비교하는 로직을 여기에 작성합니다.
    // 예제: 결과 문자열의 길이를 비교하여 더 긴 결과를 반환
    return if (result1.length > result2.length) result1 else result2
  }

}