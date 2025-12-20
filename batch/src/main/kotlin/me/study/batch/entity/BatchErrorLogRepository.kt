package me.study.batch.entity

import org.springframework.data.jpa.repository.JpaRepository

interface BatchErrorLogRepository : JpaRepository<BatchErrorLog, Long> {
}