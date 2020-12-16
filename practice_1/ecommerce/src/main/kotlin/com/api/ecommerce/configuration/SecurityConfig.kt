package com.api.ecommerce.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
//            .antMatchers("/", "/home", "/swagger-ui/**").permitAll()
//            .antMatchers("/admin", "/h2_console/**").hasRole("ADMIN").anyRequest()
//            .authenticated()
//            .and()
//            .formLogin().loginPage("/login").permitAll()
//            .and()
//            .logout().permitAll()
        http.exceptionHandling().accessDeniedPage("/403")
        http.csrf().disable()
        http.headers().frameOptions().disable()
    }

//    @Autowired
//    @Throws(Exception::class)
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication()
//            .withUser("user").password("user").roles("USER")
//            .and()
//            .withUser("admin").password("admin").roles("ADMIN")
//    }
}