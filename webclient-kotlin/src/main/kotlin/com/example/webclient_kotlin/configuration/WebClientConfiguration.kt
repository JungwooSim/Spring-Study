package com.example.webclient_kotlin.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.scheduler.Schedulers
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfiguration {
  val logger = KotlinLogging.logger {}

  val httpClient: HttpClient = HttpClient.create()
    .responseTimeout(Duration.ofSeconds(10))

  @Bean
  fun customWebClient(): WebClient {
    return WebClient.builder()
      .clientConnector(ReactorClientHttpConnector(httpClient))
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .baseUrl("http://localhost:8080")
      .filter(httpErrorLoggingFilter())
      .build()
  }

  private fun httpErrorLoggingFilter(): ExchangeFilterFunction {
    return ExchangeFilterFunction { request, next ->
      val headerToString = httpHeaderToString(request.headers())

      println(request.body().toString())

      next.exchange(request).publishOn(Schedulers.boundedElastic()).doOnNext { response ->
        response.bodyToMono(String::class.java).subscribe { body ->
          logger.info { "WebClient Filter Log - Request Url : ${request.url()}, Request Method : ${request.method()}, Request Header : $headerToString, Response status : ${response.statusCode()}, Response Body : ${body.trim()}" }
        }
      }

    }
  }

  private fun httpHeaderToString(headers: Map<String, List<String>>): String {
    return headers.entries.joinToString(separator = ", ") { "${it.key}: ${it.value.joinToString(separator = "; ")}" }
  }
}