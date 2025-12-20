package me.study.batch

import me.study.batch.dto.CustomerDto
import me.study.batch.entity.BatchErrorLog
import me.study.batch.entity.BatchErrorLogRepository
import me.study.batch.entity.Customer
import org.springframework.batch.core.SkipListener
import org.springframework.stereotype.Component

@Component
class CsvJobSkipListener(
  private val errorLogRepository: BatchErrorLogRepository
) : SkipListener<CustomerDto, Customer> {

  // 1. 읽기(Read) 단계에서 에러 발생 시 (CSV 포맷 문제 등)
  override fun onSkipInRead(t: Throwable) {
    val errorLog = BatchErrorLog(
      jobName = "csvImportJob",
      stepName = "csvImportStep",
      errorMessage = "Read Error: ${t.message}",
      content = "Unknown (Reading Failed)"
    )
    errorLogRepository.save(errorLog)
  }

  // 2. 가공(Process) 단계에서 에러 발생 시 (비즈니스 로직 위배 등)
  // item: 에러가 발생한 그 데이터 객체
  override fun onSkipInProcess(item: CustomerDto, t: Throwable) {
    val errorLog = BatchErrorLog(
      jobName = "csvImportJob",
      stepName = "csvImportStep",
      errorMessage = "Process Error: ${t.message}",
      content = item.toString() // DTO 내용을 문자열로 저장 (또는 JSON 변환)
    )
    errorLogRepository.save(errorLog)
  }

  // 3. 쓰기(Write) 단계에서 에러 발생 시 (DB 제약조건 위반 등)
  override fun onSkipInWrite(item: Customer, t: Throwable) {
    val errorLog = BatchErrorLog(
      jobName = "csvImportJob",
      stepName = "csvImportStep",
      errorMessage = "Write Error: ${t.message}",
      content = "name=${item.name}, email=${item.email}" // Entity 내용 저장
    )
    errorLogRepository.save(errorLog)
  }
}