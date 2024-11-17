package me.example.springfile.controller

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import me.example.springfile.model.FileEntity
import me.example.springfile.service.FileService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/files")
class FileController(
  private val fileService: FileService,
) {

  @PostMapping("/upload")
  @Throws(IOException::class)
  fun uploadFile(
    @RequestParam("file") file: MultipartFile
  ): FileEntity {
    return fileService.storeFile(file)
  }

  @GetMapping("/download/{id}")
  @Throws(IOException::class)
  fun downloadFile(
    @PathVariable id: Long
  ): ResponseEntity<ByteArray> {
    val fileEntity: FileEntity = fileService.getFile(id)
    val path: Path = Paths.get(fileEntity.filePath)
    val data = Files.readAllBytes(path)
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(fileEntity.fileType))
      .header(HttpHeaders.CONTENT_DISPOSITION, ("attachment; filename=\"" + fileEntity.fileName) + "\"")
      .body<ByteArray>(data)
  }
}