package com.api.ecommerce.configurations

import com.api.ecommerce.errors.AuthEntryPointJwt
import com.api.ecommerce.securities.JWTAuthenticationFilter
import com.api.ecommerce.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
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

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    lateinit var unauthorizedHandler: AuthEntryPointJwt

    @Bean
    fun authenticationJwtTokenFilter(): JWTAuthenticationFilter {
        return JWTAuthenticationFilter()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun unauthorizedHandler(): AuthEntryPointJwt {
        return AuthEntryPointJwt()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun userDetailsService(): UserDetailsServiceImpl {
        return userDetailsService
    }

    // Note: if we want to manage more roles, probably we need to create a UserRole entity class
    // and keep this logic isolated somewhere
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return roleHierarchy
    }

    private val PERMITTED = listOf(
        // -- swagger ui
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/webjars/**",
        "/api-docs/**",
        "/health",
        "/info",
        "/users/*",
        "/users",
        "/register",
        "/login",
        "/admin/login"
    )

    @Throws(Exception::class)
    override fun configure(security: AuthenticationManagerBuilder) {
        security
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(*PERMITTED.toTypedArray())
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
            .cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/h2", "/h2/**", "/error").permitAll()
            .antMatchers(*PERMITTED.toTypedArray()).permitAll()
            .anyRequest().authenticated()
            .and()
            .logout().permitAll()

        // Custom JWT based security filter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER")
            .and()
            .withUser("admin").password("password").roles("ADMIN")
    }

    // Define error messages
    companion object {
        const val NO_AUTH = "noauth"
        const val BASIC_AUTH = "basicauth"
        const val JWT_AUTH = "jwtauth"
        const val LDAP_AUTH = "ldapauth"
    }
}