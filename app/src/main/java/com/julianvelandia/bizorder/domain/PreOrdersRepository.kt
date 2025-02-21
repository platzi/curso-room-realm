package com.julianvelandia.bizorder.domain

import kotlinx.coroutines.flow.Flow

interface PreOrdersRepository {
    suspend fun savePreOrder(preorder: PreOrder): Result<Unit>

    fun getPreOrders(): Flow<Result<List<PreOrder>>>

    suspend fun onDelete(id: Long)

    suspend fun retrySync(id: Long)
}