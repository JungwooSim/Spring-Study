package me.study.batch.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "batch_error_log")
class BatchErrorLog(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  val jobName: String,
  val stepName: String,

  @Column(length = 1000)
  val errorMessage: String?,  // 왜 실패했는지?

  @Column(columnDefinition = "TEXT")
  val content: String?,       // 실패한 데이터 원본 (JSON 등)

  val errorDate: LocalDateTime = LocalDateTime.now()
)