package com.api.ecommerce.domains

import javax.persistence.*

@Entity
@Table(name="\"tb_product\"")
data class Product(
    @Column(nullable = false)
    var name: String = "",
    var description: String = "",
    var unit: Int = 0,
    @Column(nullable = false)
    var price: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, updatable = false)
    var category: Category = Category(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0)