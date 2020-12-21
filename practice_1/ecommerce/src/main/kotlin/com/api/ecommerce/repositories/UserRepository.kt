package com.api.ecommerce.repositories

import com.api.ecommerce.domains.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findUserByEmail(@Param("email") email: String): User?
}