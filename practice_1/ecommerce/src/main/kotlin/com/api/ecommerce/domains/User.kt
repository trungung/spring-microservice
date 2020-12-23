package com.api.ecommerce.domains

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name="\"tb_user\"")
data class User(

    @NotBlank(message = "Name is mandatory")
    @Column(name = "user_name")
    var userName: String,

    @Email(message = "Email should be valid")
    @Column(name = "email")
    var email: String,

    @Min(value = 9, message="Phone number must be equal or greater than 9")
    @Max(value = 12, message="Phone number must be equal or less than 12")
    @Column(name = "phone")
    var phone: String,

    @Column(name = "role")
    var role: Int = 0,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Long = 0) {

    constructor() : this("", "", "", 0, 0)

    constructor(userName: String, email: String, phone: String, role: Int) : this() {
        this.userName = userName
        this.email = email
        this.phone = phone
        this.role = role
    }

    override fun toString(): String {
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
}
