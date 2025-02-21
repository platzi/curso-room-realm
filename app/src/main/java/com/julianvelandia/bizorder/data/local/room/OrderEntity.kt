package com.julianvelandia.bizorder.data.local.room
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val customerName: String,
    val item: String,
    val total: Double,
    val imageUrl: String
)