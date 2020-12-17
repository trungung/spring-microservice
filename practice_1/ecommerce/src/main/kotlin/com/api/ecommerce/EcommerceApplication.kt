package com.api.ecommerce

import com.api.ecommerce.domains.User
import com.api.ecommerce.repositories.UserRepository
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
		val user = User()
		user.userName = "ADMIN"
		user.email = "admin@admin.com"
		user.phone = "123456789"
		user.setAdminRole()
		userRepository.save(user)
	}
}

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)
}