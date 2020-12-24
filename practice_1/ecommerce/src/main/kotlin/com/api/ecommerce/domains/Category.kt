package com.api.ecommerce.domains

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name="\"tb_category\"")
data class Category(

    @Column(nullable = false)
    @Size(max = 25)
    var name: String = "",

    var description: String = "",

    // Here mappedBy indicates that the owner is in the other side
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: Set<Product> = setOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0)
