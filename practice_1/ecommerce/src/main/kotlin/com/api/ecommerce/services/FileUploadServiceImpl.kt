package com.api.ecommerce.services

import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileUploadServiceImpl: FileUploadService {

    val location = Paths.get("")

    override fun uploadFile(file: MultipartFile) {
        val fileName = file.originalFilename?.let { StringUtils.cleanPath(it) }
        if (fileName.isNullOrEmpty()) {
            throw IOException("File is empty $fileName")
        } else {
            Files.copy(file.inputStream, location.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}