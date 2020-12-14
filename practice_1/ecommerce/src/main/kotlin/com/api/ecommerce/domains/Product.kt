package com.api.ecommerce.domains

data class Product(
    var id: Int,
    var name: String,
    var description: String,
    var category_id: Int,
    var unit: Int,
    var price: Double)
