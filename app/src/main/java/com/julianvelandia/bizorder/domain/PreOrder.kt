package com.julianvelandia.bizorder.domain

data class PreOrder(
    val id: Long = 0L,
    val customerName: String = "",
    val product: String = "",
    val isSent: Boolean = false
)