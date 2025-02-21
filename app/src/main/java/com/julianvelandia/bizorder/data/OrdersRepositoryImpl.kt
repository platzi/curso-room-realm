package com.julianvelandia.bizorder.data

import com.julianvelandia.bizorder.data.local.LocalDataStorage
import com.julianvelandia.bizorder.data.local.toDomain
import com.julianvelandia.bizorder.data.local.toEntity
import com.julianvelandia.bizorder.data.remote.RemoteDataStorage
import com.julianvelandia.bizorder.data.remote.toDomain
import com.julianvelandia.bizorder.domain.Order
import com.julianvelandia.bizorder.domain.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class OrdersRepositoryImpl(
    private val remoteDataStorage: RemoteDataStorage,
    private val localDataStorage: LocalDataStorage
) : OrdersRepository {

    override fun getOrder(): Flow<Result<List<Order>>> {
        return localDataStorage.getAllOrdersRoom()
            .map { localOrders ->
                Result.success(localOrders.map { it.toDomain() })
            }
            .onStart {
                val remoteResult = remoteDataStorage.getOrders()
                    .mapCatching { dtoList ->
                        dtoList.map { it.toDomain() }
                    }

                remoteResult.getOrNull()?.let { orders ->
                    localDataStorage.upsertRoom(orders.map { it.toEntity() })
                }
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override suspend fun getOrderById(id: String) = localDataStorage.getOrderByIdRoom(id)
        .mapCatching {
            it?.toDomain()
        }


}