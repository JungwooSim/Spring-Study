package com.example.webclient_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication(
	scanBasePackages = [
		"com.example.webclient_kotlin"
	]
)
class WebclientKotlinApplication

fun main(args: Array<String>) {
	runApplication<WebclientKotlinApplication>(*args)
}
