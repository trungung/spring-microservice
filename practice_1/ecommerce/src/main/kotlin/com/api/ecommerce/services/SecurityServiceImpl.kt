package com.api.ecommerce.services

import com.api.ecommerce.daos.UserRepository
import com.api.ecommerce.domains.User
import com.api.ecommerce.securities.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional


/**
 * Spring-oriented Implementation for {@link SecurityService}
 * Also it implements {@link UserDetailsService} being required by the Spring Security framework.
 */
@Service
class SecurityServiceImpl: SecurityService, UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Transactional
    override fun authenticate(username: String, password: String): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user: User = loadUserByUsername(username)
        return JwtUtils.generateToken(user.userId, user.username, user.role)
    }

    @Transactional
    override fun authenticate(token: String) {
        val claims = JwtUtils.parseToken(token)

        val user = User(claims[JwtUtils.TOKEN_CLAIM_USERNAME].toString(), "", "", claims[JwtUtils.TOKEN_CLAIM_ROLES].toString(), claims.subject.toLong())
        // Setting up Authentication...
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    override fun getCurrentUser(): User {
        return (SecurityContextHolder.getContext().authentication.principal as User)
    }

    @Transactional
    override fun loadUserByUsername(username: String): User {
        return userRepository.findByUserName(username)
            .orElseThrow { UsernameNotFoundException("Invalid username or password.") }
    }
}