package me.example.springminio.controller

import me.example.springminio.service.FileService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileController(
  private val fileService: FileService
) {

  @PostMapping("/file")
  fun fileUpload(file: MultipartFile) {
    fileService.upload(file = file)
  }
}