package me.example.springminio.service

import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.ServerSideEncryption
import io.minio.ServerSideEncryptionCustomerKey
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {
  fun upload(file: MultipartFile) {
    val minioClient = MinioClient.builder()
      .endpoint("http://localhost:9000")
      .build()

    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(256)
    val secretKey = keyGenerator.generateKey()
    val sse = ServerSideEncryptionCustomerKey(secretKey)

    val putObjectArgs = PutObjectArgs.builder()
      .bucket("my-test-bucket")
      .`object`(UUID.randomUUID().toString())
      .stream(file.inputStream, file.size, -1)
      .sse(sse)
      .build()

    minioClient.putObject(putObjectArgs)
  }
}
