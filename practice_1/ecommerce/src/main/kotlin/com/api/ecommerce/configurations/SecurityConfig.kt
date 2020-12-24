package com.api.ecommerce.configurations

import com.api.ecommerce.securities.JWTAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig: WebSecurityConfigurerAdapter() {

    private val PERMITTED = listOf(
        "/configuration/**",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**",
        "/api-docs/**",
        "/health",
        "/info",
        "/users",
        "/users/*",
        "/categories/*",
        "/products/*",
        "/customers/*"
    )

    @Throws(Exception::class)
    override fun configure(security: AuthenticationManagerBuilder) {
        security
            .userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // 1) Enabling Frames because it's required for h2. Note: this shouldn't be exposed to prod.
        // 2) Setting exception handler in Filters
        // 3) Allowing the following actions without authentication:
        // 3.a) Processing OPTIONS for all endpoints
        // 3.b) Processing POST on /login
        // 3.c) Accessing to h2 web console. Note: this shouldn't be exposed to prod.
        // 4) All the other requests need to be authenticated
        // 5) Adding to the filter chain the JWT Authentication Filter
        // 1) Enabling Frames because it's required for h2. Note: this shouldn't be exposed to prod.
        // 2) Setting exception handler in Filters
        // 3) Allowing the following actions without authentication:
        // 3.a) Processing OPTIONS for all endpoints
        // 3.b) Processing POST on /login
        // 3.c) Accessing to h2 web console. Note: this shouldn't be exposed to prod.
        // 4) All the other requests need to be authenticated
        // 5) Adding to the filter chain the JWT Authentication Filter
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers("/h2", "/h2/**", "/error").permitAll()
            .antMatchers(*PERMITTED.toTypedArray()).permitAll()
            .anyRequest().authenticated()

        // Custom JWT based security filter
        http
            .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        // disable page caching
        http.headers().cacheControl();
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user").password("user").roles("USER")
            .and()
            .withUser("admin").password("admin").roles("ADMIN")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
    
    // Define error messages
    companion object {
        const val NO_AUTH = "noauth"
        const val BASIC_AUTH = "basicauth"
        const val JWT_AUTH = "jwtauth"
        const val LDAP_AUTH = "ldapauth"
    }
}