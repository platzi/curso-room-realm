package com.julianvelandia.bizorder.data

import com.julianvelandia.bizorder.data.local.LocalDataStorage
import com.julianvelandia.bizorder.data.local.realm.PreOrderObject
import com.julianvelandia.bizorder.data.local.room.PreOrderEntity
import com.julianvelandia.bizorder.data.local.toDomain
import com.julianvelandia.bizorder.data.remote.RemoteDataStorage
import com.julianvelandia.bizorder.domain.PreOrder
import com.julianvelandia.bizorder.domain.PreOrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreOrderRepositoryImpl(
    private val remoteDataStorage: RemoteDataStorage,
    private val localDataStorage: LocalDataStorage
) : PreOrdersRepository {
    override suspend fun savePreOrder(preOrder: PreOrder) =
        remoteDataStorage.savePreOrder().also { result ->
            localDataStorage.savePreOrderRealm(
                /* PreOrderEntity(
                     customerName = preOrder.customerName,
                     item = preOrder.product,
                     isSent = result.isSuccess
                 )
                 */
                PreOrderObject().apply {
                    customerName = preOrder.customerName
                    item = preOrder.product
                    isSent = result.isSuccess
                }
            )
        }

    override fun getPreOrders(): Flow<Result<List<PreOrder>>> {
        return localDataStorage.getAllPreOrdersRealm()
            .map { orderEntities ->
                runCatching { orderEntities.map { it.toDomain() } }
            }
    }

    override suspend fun onDelete(id: Long) = localDataStorage.deleteByIdRealm(id)

    override suspend fun retrySync(id: Long) {
        val result = remoteDataStorage.savePreOrder()
        if (result.isSuccess) {
            localDataStorage.retrySyncRealm(id, true)
        }
    }


}