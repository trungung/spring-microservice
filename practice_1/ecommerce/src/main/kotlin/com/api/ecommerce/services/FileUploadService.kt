package com.api.ecommerce.services

import org.springframework.web.multipart.MultipartFile

interface FileUploadService {

    fun uploadFile(file: MultipartFile)
}