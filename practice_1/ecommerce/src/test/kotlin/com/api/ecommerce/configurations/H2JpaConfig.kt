package com.api.ecommerce.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["com.api.ecommerce.repositories"])
@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
class H2JpaConfig {
}