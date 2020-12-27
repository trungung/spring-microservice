package com.api.ecommerce.dtos.requests

class ProductRequest(
    val name: String,
    val description: String,
    val unit: Int,
    val price: Double,
    val categoryId: Long)