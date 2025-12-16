package me.study.batch.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Customer(
  var name: String,
  var email: String,
  var age: Int,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 전처리 로직이 포함된 보조 생성자 예시 (필요시)
  constructor(name: String, email: String, age: Int, toUpperCase: Boolean) : this(
    name = if (toUpperCase) name.uppercase() else name,
    email = email,
    age = age
  )
}