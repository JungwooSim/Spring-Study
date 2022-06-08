package com.example.restfulapikotlinsample1.persistence

import com.example.restfulapikotlinsample1.model.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>