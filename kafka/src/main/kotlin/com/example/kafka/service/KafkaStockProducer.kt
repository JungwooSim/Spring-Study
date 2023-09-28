package com.example.kafka.service

import com.example.kafka.dto.Stock
import jakarta.annotation.PostConstruct
import org.springframework.core.io.ResourceLoader
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class KafkaStockProducer(
  private val resourceLoader: ResourceLoader,
  private val kafkaTemplate: KafkaTemplate<String, Stock>
) {

  @PostConstruct
  fun postConstruct() {
    val stocks = this.readStockFile()

    stocks.forEach {
      kafkaTemplate.send("stock", it)
    }
  }

  /**
   * file read
   */
  fun readStockFile(): List<Stock> {
    val stocks = ArrayList<Stock>()
    val resource = resourceLoader.getResource("classpath:" + "stock-data/MSFT.csv")
    val inputStream = resource.inputStream

    var line: String?
    var isFirstLine = true
    val reader = BufferedReader(InputStreamReader(inputStream))
    while (reader.readLine().also { line = it } != null) {
      if (isFirstLine) {
        isFirstLine = false
        continue
      }

      val read = line!!.split(",")

      stocks.add(
        Stock(
          date = read[0],
          open = read[2].toDouble(),
          high = read[3].toDouble(),
          close = read[4].toDouble(),
          average = read[5].toDouble(),
          volume = read[6].toLong(),
        )
      )

    }

    return stocks
  }

}