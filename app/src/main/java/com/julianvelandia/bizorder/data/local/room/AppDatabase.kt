package com.julianvelandia.bizorder.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [OrderEntity::class, PreOrderEntity::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun preOrderDao(): PreOrderDao
}