package com.api.ecommerce.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    private val PERMITTED = listOf(
        "/v1/auth/person",
        "/v1/auth/group",
        "/v1/time",
        "/v1/jwt",
        "/v1/debug/**",
        "/v2/api-docs",
        "/configuration/**",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/api-docs/**",
        "/health",
        "/info",
        "/users/*",
        "/customers/*"
    )

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable()
        // Set session management to statelessz
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        // Set unauthorized requests exception handler
        http.exceptionHandling()
            .authenticationEntryPoint { request: HttpServletRequest, response: HttpServletResponse, ex: AuthenticationException ->
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.message
                )
            }
            .and()

        http.authorizeRequests()
            // public endpoints
            .antMatchers("/admin", "/h2_console/**").hasRole("ADMIN")
            .antMatchers(*PERMITTED.toTypedArray()).permitAll()
            //private endpoints
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout().permitAll()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user").password("user").roles("USER")
            .and()
            .withUser("admin").password("admin").roles("ADMIN")
    }

    // Define error messages
    companion object {
        const val NO_AUTH = "noauth"
        const val BASIC_AUTH = "basicauth"
        const val JWT_AUTH = "jwtauth"
        const val LDAP_AUTH = "ldapauth"
    }
}