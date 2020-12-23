package com.api.ecommerce.dto.requests

class ProductRequest(
    val name: String,
    val description: String,
    val unit: Int,
    val price: Double,
    val categoryId: Long)