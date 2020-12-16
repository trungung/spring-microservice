package com.api.ecommerce.domains

import javax.persistence.*

@Entity
@Table(name="\"tb_user\"")
data class User(
    @Column(name = "user_name")
    var userName: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "role")
    var role: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Int = -1
) {

    constructor() : this("", "", "", 0, -1)

    override fun toString(): String{
        return "User [id= + ${this.userId} + , name= + ${this.userName} + , email= + ${this.email} + , phone= + ${this.phone} + ]";
    }

    fun setAdminRole() {
        this.role = Role.ADMIN.value
    }

    fun setBusinessRole() {
        this.role = Role.BUSINESS.value
    }

    fun setCustomerRole() {
        this.role = Role.CUSTOMER.value
    }

    fun createUser(userName: String, email: String, phone: String, role: Int) {
        this.userName = userName
        this.email = email
        this.phone = phone
        this.role = role
    }
}
