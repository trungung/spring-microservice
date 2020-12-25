package com.api.ecommerce

import com.api.ecommerce.daos.UserRepository
import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct


@SpringBootApplication
class EcommerceApplication {
	@Autowired
	lateinit var userRepository: UserRepository

	@PostConstruct
	private fun initDb() {
//		val user = User()
//		user.username = "ADMIN"
//		user.password = "ADMIN"
//		user.email = "admin@admin.com"
//		user.phone = "123456789"
//		user.role = Role.ADMIN.value
//		userRepository.save(user)
	}
}

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)
}