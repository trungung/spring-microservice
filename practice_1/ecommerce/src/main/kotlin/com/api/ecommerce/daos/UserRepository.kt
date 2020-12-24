package com.api.ecommerce.daos

import com.api.ecommerce.domains.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Spring Data JPA Repository for {@link User} entity
 */
@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByEmail(email: String): User?

    /**
     * Finds a User through the given username.
     *
     * @param username the username to look for
     * @return the User that was found (if any)
     */
    fun findByUsername(username: String): Optional<User>
}