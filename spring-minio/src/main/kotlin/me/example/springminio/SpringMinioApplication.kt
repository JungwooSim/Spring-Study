package me.example.springminio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringMinioApplication

fun main(args: Array<String>) {
  runApplication<SpringMinioApplication>(*args)
}
