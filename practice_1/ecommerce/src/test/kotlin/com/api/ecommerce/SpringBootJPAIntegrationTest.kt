package com.api.ecommerce

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import com.api.ecommerce.configuration.H2JpaConfig
import com.api.ecommerce.domains.User
import com.api.ecommerce.repositories.UserRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest
class SpringBootJPAIntegrationTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun givenUserEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        val user = User()
        user.userName = "userName"
        user.email = "email"
        user.phone = "123456789"
        user.setAdminRole()

        val userEntity: User = userRepository.save(user)
        val foundEntity: User = userRepository.findById(userEntity.userId).get()
        assertNotNull(foundEntity)
        assertEquals(userEntity.phone, foundEntity.phone)
    }
}