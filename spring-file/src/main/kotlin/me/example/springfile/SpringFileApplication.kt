package me.example.springfile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringFileApplication

fun main(args: Array<String>) {
	runApplication<SpringFileApplication>(*args)
}
