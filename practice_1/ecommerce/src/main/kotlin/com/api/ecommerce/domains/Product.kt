package com.api.ecommerce.domains

import javax.persistence.*

@Entity
data class Product(
    var name: String = "",
    var description: String = "",
    var unit: Int = 0,
    var price: Double = 0.0,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category = Category(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1)