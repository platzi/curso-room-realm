package com.julianvelandia.bizorder.domain

import kotlinx.coroutines.flow.Flow
interface OrdersRepository {
    fun getOrder(): Flow<Result<List<Order>>>

    suspend fun getOrderById(id: String): Result<Order?>
}