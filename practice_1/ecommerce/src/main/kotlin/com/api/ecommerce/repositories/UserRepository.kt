package com.api.ecommerce.repositories

import com.api.ecommerce.domains.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Spring Data JPA Repository for {@link User} entity
 */
@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    /**
     * Finds a User through the given username.
     *
     * @param username the username to look for
     * @return the User that was found (if any)
     */
    fun findByUserName(username: String): Optional<User>
}