package me.example.springfile.service

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import me.example.springfile.model.FileEntity
import me.example.springfile.model.FileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class FileService(
  private val fileRepository: FileRepository,
) {
  private val uploadDir = "uploads"

  @Throws(IOException::class)
  fun storeFile(file: MultipartFile): FileEntity {
    val copyLocation: Path = Paths.get(uploadDir + "/" + file.originalFilename)
    Files.copy(file.inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

    val fileEntity = FileEntity(
      fileName = file.originalFilename!!,
      fileType = file.contentType!!,
      filePath = copyLocation.toString(),
      uploadTime = LocalDateTime.now()
    )

    return fileRepository.save(fileEntity)
  }

  fun getFile(id: Long): FileEntity {
    return fileRepository.findByIdOrNull(id) ?: throw RuntimeException("File not found with id $id")
  }
}