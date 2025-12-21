package me.study.batch.job_config

import jakarta.persistence.EntityManagerFactory
import java.lang.Exception
import javax.sql.DataSource
import me.study.batch.CsvJobSkipListener
import me.study.batch.dto.CustomerDto
import me.study.batch.entity.Customer
import org.springframework.batch.core.Job
import org.springframework.batch.core.SkipListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class CsvImportJobConfig(
  private val jobRepository: JobRepository,
  private val transactionManager: PlatformTransactionManager,
  private val entityManagerFactory: EntityManagerFactory,
  private val csvJobSkipListener: CsvJobSkipListener,
  private val dataSource: DataSource // EntityManagerFactory 대신 DataSource 주입

) {

  // 1. Job 정의
  @Bean
  fun csvImportJob(): Job {
    return JobBuilder("csvImportJob_V3", jobRepository)
//      .incrementer(RunIdIncrementer())
      .start(csvImportStep())
      .build()
  }

  // 2. Step 정의
  @Bean
  fun csvImportStep(): Step {
    return StepBuilder("csvImportStep", jobRepository)
      .chunk<CustomerDto, Customer>(1000, transactionManager) // <Input, Output> 타입 명시
      .reader(csvReader())
      .processor(csvProcessor())
      .writer(csvWriter())
      .faultTolerant()
      .skip(Exception::class.java)
      .skipLimit(Int.MAX_VALUE)
      .listener(csvJobSkipListener)

      .listener(object : SkipListener<CustomerDto, Customer> { // 2개 사용해도 무방하다.
        override fun onSkipInRead(t: Throwable) {
          println("읽기 중 에러 발생: ${t.message}")
        }

        // Processor에서 에러가 나서 Skip 될 때 실행됨
        override fun onSkipInProcess(item: CustomerDto, t: Throwable) {
          println("처리 중 에러 발생 (Skipped): $item, 원인: ${t.message}")
          // 실무에서는 여기서 별도 에러 테이블(BATCH_ERROR_LOG)에 insert 하기도 함
        }

        override fun onSkipInWrite(item: Customer, t: Throwable) {
          println("쓰기 중 에러 발생: $item, 원인: ${t.message}")
        }
      })
      .build()
  }

  // 3. Reader: CSV 파일 읽기
  @Bean
  fun csvReader(): FlatFileItemReader<CustomerDto> {
    return FlatFileItemReaderBuilder<CustomerDto>()
      .name("csvReader")
      .resource(ClassPathResource("customers.csv"))
      .delimited()
      .names("name", "email", "age")
      .targetType(CustomerDto::class.java) // Kotlin 클래스 참조
      .linesToSkip(1)
      .build()
  }

  // 4. Processor: 데이터 가공 (DTO -> Entity)
  @Bean
  fun csvProcessor(): ItemProcessor<CustomerDto, Customer> {
    return ItemProcessor { item ->
      // 예제 전처리: 나이가 30 미만이면 null을 반환하여 필터링 (DB 저장 안 함)
//      if (item.age < 30) {
//        return@ItemProcessor null
//      }

      // 실패 테스트
      if (item.name == "ERROR") {
        throw IllegalArgumentException("잘못된 이름입니다: ${item.name}")
      }

      // 이름 대문자 변환 후 Entity 생성
      Customer(
        name = item.name.uppercase(),
        email = item.email,
        age = item.age
      )
    }
  }

  // 5. Writer: DB 저장
//  @Bean
//  fun csvWriter(): JpaItemWriter<Customer> {
//    return JpaItemWriterBuilder<Customer>()
//      .entityManagerFactory(entityManagerFactory)
//      .build()
//  }

  // 5-2 Bulk Writer
  // [핵심 변경] JpaItemWriter -> JdbcBatchItemWriter
  @Bean
  fun csvWriter(): JdbcBatchItemWriter<Customer> {
    return JdbcBatchItemWriterBuilder<Customer>()
      .dataSource(dataSource) // DataSource 설정
      // 실제 실행될 SQL 작성 (Values 절에 :필드명 사용)
      .sql("INSERT INTO customer (name, email, age) VALUES (:name, :email, :age)")
      // Customer 객체의 필드명(name, email, age)을 SQL 파라미터(:name...)에 자동 매핑
      .beanMapped()
      .build()
  }
}