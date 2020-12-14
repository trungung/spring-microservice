package com.api.ecommerce.domains

import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int,

    @Column(name = "user_name")
    var userName: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "role")
    val role: Role) {
    constructor()
}

inline fun createAdminUser(userName: String, email: String, phone: String):User {
    return User(0, userName, email, phone, Role.ADMIN)
}

inline fun createBusinessUser(userName: String, email: String, phone: String):User {
    return User(0, userName, email, phone, Role.BUSINESS)
}

inline fun createCustomerUser(userName: String, email: String, phone: String):User {
    return User(0, userName, email, phone, Role.CUSTOMER)
}

