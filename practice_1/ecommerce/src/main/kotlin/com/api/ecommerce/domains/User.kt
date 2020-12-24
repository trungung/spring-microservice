package com.api.ecommerce.domains

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name="\"tb_user\"")
data class User(

    @NotBlank(message = "Name is mandatory")
    @Column(name = "user_name", nullable = false, unique = true)
    var userName: String,

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Min(value = 9, message="Phone number must be equal or greater than 9")
    @Max(value = 12, message="Phone number must be equal or less than 12")
    @Column(name = "phone", nullable = false, unique = true)
    var phone: String,

    @Column(name = "role", nullable = false)
    var role: String = Role.CUSTOMER.value,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Long = 0): UserDetails {

    constructor() : this("", "", "", "", 0)

    constructor(userName: String, email: String, phone: String, role: String) : this() {
        this.userName = userName
        this.email = email
        this.phone = phone
        this.role = role
    }

    override fun toString(): String {
        return "User [id= + ${this.userId} + , name= + ${this.userName} + , email= + ${this.email} + , phone= + ${this.phone} + ]";
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userName
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
