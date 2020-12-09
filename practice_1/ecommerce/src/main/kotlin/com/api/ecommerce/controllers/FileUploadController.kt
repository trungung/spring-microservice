package com.api.ecommerce.controllers

import com.api.ecommerce.services.FileUploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileUploadController {

    @Autowired
    lateinit var fileUploadService: FileUploadService

    @ResponseBody
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): Map<String, Any> {
        fileUploadService.uploadFile(file)
        val map = LinkedHashMap<String, Any>()
        map["result"] = "Uploaded"
        return map
    }
}