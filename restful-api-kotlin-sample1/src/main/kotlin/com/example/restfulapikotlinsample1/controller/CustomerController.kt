package com.example.restfulapikotlinsample1.controller

import com.example.restfulapikotlinsample1.model.Customer
import com.example.restfulapikotlinsample1.persistence.CustomerRepository
import com.example.restfulapikotlinsample1.service.ApiService
import java.time.LocalDateTime
import java.util.concurrent.ExecutionException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/customers")
class CustomerController(
    val repository: CustomerRepository,
    val apiService: ApiService
) {

    @GetMapping
    fun findAll(): String {
        repository.findAll()

        println("customer 1 NOW : ${LocalDateTime.now()}")
        return "OKOK"
    }

    @GetMapping("/2")
    fun findAll2(): String {
        repository.findAll()

        println("customer 2 NOW : ${LocalDateTime.now()}")
        return "OKOK"
    }

    @PostMapping
    fun addCustomer(@RequestBody customer: Customer) = repository.save(customer)

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable id: Long, @RequestBody customer: Customer) {
        assert(customer.id == id)
        repository.save(customer)
    }

    @DeleteMapping("/{id}")
    fun removeCustomer(@PathVariable id: Long) = repository.deleteById(id)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = repository.findById(id)

    @GetMapping("/test")
    @Throws(ExecutionException::class, InterruptedException::class)
    fun compareApis(): String? {
        return apiService.a()
    }
}