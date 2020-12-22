package com.api.ecommerce.domains

import javax.persistence.*

@Entity
data class Product(
    var name: String,
    var description: String,
    var categoryId: Long,
    var unit: Int,
    var price: Double,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1)
