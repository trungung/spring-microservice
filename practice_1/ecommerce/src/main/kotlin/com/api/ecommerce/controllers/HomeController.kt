package com.api.ecommerce.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HomeController {

    @ResponseBody
    @GetMapping("")
    fun test(): String {
        return "ECOMMERCE!"
    }
}