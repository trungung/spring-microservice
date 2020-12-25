package com.api.ecommerce.services

import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.security.core.userdetails.UserDetails


interface UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    fun loadUserByUsername(username: String): UserDetails
}