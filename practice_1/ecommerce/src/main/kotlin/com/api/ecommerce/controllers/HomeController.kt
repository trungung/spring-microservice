package com.api.ecommerce.controllers

import com.api.ecommerce.services.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.LinkedHashMap

import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/")
class HomeController {

    @Autowired
    lateinit var securityService: SecurityService

    @ResponseBody
    @GetMapping("")
    fun test(): String {
        return "ECOMMERCE!"
    }

    @ResponseBody
    @RequestMapping("/security/generate/token")
    fun generateToken(@RequestParam(value = "subject") subject: String): Map<String, Any> {
        val token = securityService.createToken(subject, (2 * 1000 * 60).toLong())
        val map: MutableMap<String, Any> = LinkedHashMap()
        map["result"] = token
        return map
    }

    @ResponseBody
    @RequestMapping("/security/get/subject")
    fun getSubject(@RequestParam(value = "token") token: String): Map<String, Any> {
        val subject: String = securityService.getSubject(token)
        val map: MutableMap<String, Any> = LinkedHashMap()
        map["result"] = subject
        return map
    }
}