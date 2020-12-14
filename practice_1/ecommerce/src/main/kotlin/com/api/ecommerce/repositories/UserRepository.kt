package com.api.ecommerce.repositories

import com.api.ecommerce.domains.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {

}