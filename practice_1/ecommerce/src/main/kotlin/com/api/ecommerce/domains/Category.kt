package com.api.ecommerce.domains

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Category(

    var name: String,

    var description: String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = -1) {

    constructor() : this("", "", -1)
}
