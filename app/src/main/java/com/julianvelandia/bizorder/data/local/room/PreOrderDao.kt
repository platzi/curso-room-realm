package com.julianvelandia.bizorder.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreOrderDao {
    @Insert
    suspend fun insert(preOrder: PreOrderEntity)

    @Query("SELECT * FROM pre_orders")
    fun getAllPreOrders(): Flow<List<PreOrderEntity>>


    @Query("DELETE FROM pre_orders WHERE id = :preOrderId")
    suspend fun deleteById(preOrderId: Long)

    @Query("UPDATE pre_orders SET isSent = :isSent WHERE id = :preOrderId")
    suspend fun updateIsSent(preOrderId: Long, isSent: Boolean)
}