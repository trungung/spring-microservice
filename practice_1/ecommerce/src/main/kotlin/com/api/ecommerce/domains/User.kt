package com.api.ecommerce.domains

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import java.util.stream.Collectors




@Entity
@Table(name="\"tb_user\"")
data class User(

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var phone: String,

    @Column(nullable = false)
    var role: String = Role.CUSTOMER.value,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Long = 0,

    @Column(nullable = false, unique = true)
    var userName: String = "",

    @Column(nullable = false)
    var pass: String = ""
    ): UserDetails {

    constructor() : this("", "", "", 0)

    constructor(email: String, phone: String, role: String) : this() {
        this.email = email
        this.phone = phone
        this.role = role
    }

    override fun toString(): String {
        return "User [id= + ${this.userId} + , email= + ${this.email} + , phone= + ${this.phone} + ]"
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//        return this.authorities.stream()
//            .map { authority -> SimpleGrantedAuthority("ROLE_" + role.toUpperCase()) }
//            .collect(Collectors.toList())
    }

    fun setUsername(name: String) {
        this.userName = name
    }

    fun setPassword(password: String) {
        this.pass = password
    }

    override fun getUsername(): String {
        return this.userName
    }

    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
