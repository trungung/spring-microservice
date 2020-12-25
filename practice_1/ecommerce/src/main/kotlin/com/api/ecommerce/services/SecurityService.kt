package com.api.ecommerce.services

import com.api.ecommerce.domains.User
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException

/**
 * Security Service to authenticate a User.
 * Users can be authenticated by providing either their credentials or auth token.
 */
interface SecurityService {
    /**
     * Authenticates a User through their credentials.
     *
     * @param username the username to login
     * @param password the password to validate
     * @exception AuthenticationException when user credentials are invalid
     * @return an auth token that can be used for reference
     */
    fun authenticate(username: String, password: String): String

    /**
     * Authenticates a User through an auth token.
     *
     * @param token the auth token to validate
     * @exception BadCredentialsException when auth token is invalid
     */
    fun authenticate(token: String)

    /**
     * Gets the current logged in User.
     *
     * @return the logged in User information
     */
    fun getCurrentUser(): User
}