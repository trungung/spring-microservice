package com.api.ecommerce.controllers

import org.springframework.security.access.prepost.PreAuthorize

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestRestAPIs {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun userAccess(): String {
        return ">>> User Contents!"
    }

    @GetMapping("/pm")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    fun projectManagementAccess(): String {
        return ">>> Project Management Board"
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return ">>> Admin Contents"
    }
}