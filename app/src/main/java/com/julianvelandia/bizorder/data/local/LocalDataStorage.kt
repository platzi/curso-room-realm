package com.julianvelandia.bizorder.data.local

import com.julianvelandia.bizorder.data.local.realm.OrderObject
import com.julianvelandia.bizorder.data.local.realm.OrderRealm
import com.julianvelandia.bizorder.data.local.realm.PreOrderObject
import com.julianvelandia.bizorder.data.local.realm.PreOrderRealm
import com.julianvelandia.bizorder.data.local.room.OrderDao
import com.julianvelandia.bizorder.data.local.room.OrderEntity
import com.julianvelandia.bizorder.data.local.room.PreOrderDao
import com.julianvelandia.bizorder.data.local.room.PreOrderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStorage @Inject constructor(
    private val orderDao: OrderDao,
    private val preOrderDao: PreOrderDao,
    private val orderRealm: OrderRealm,
    private val preOrderRealm: PreOrderRealm
) {

    //Orders ROOM
    suspend fun upsertRoom(
        orderEntities: List<OrderEntity>
    ) = orderDao.upsert(orderEntities)

    fun getAllOrdersRoom() = orderDao.getAllOrders()

    suspend fun getOrderByIdRoom(orderId: String): Result<OrderEntity?> = runCatching {
        orderDao.getOrderById(orderId)
    }


    //Orders Realm

    suspend fun insertRealm(
        ordersObject: List<OrderObject>
    ) = orderRealm.addOrders(ordersObject)

    fun getAllOrdersRealm(): Flow<List<OrderObject>> = orderRealm.getAllOrders()

    suspend fun getOrderByIdRealm(orderId: String): Result<OrderObject?> = runCatching {
        orderRealm.getOrderById(orderId)
    }


    //PreOrders ROOM
    suspend fun savePreOrderRoom(
        preOrderEntity: PreOrderEntity
    ) = preOrderDao.insert(preOrderEntity)

    fun getAllPreOrdersRoom() = preOrderDao.getAllPreOrders()

    suspend fun deleteByIdRoom(id: Long) = preOrderDao.deleteById(id)

    suspend fun retrySyncRoom(id: Long, isSent: Boolean) = preOrderDao.updateIsSent(id, isSent)


    //PreOrders REALM
    suspend fun savePreOrderRealm(preOrder: PreOrderObject) = preOrderRealm.insert(preOrder)

    fun getAllPreOrdersRealm(): Flow<List<PreOrderObject>> = preOrderRealm.getAllPreOrders()

    suspend fun deleteByIdRealm(id: Long) = preOrderRealm.deleteById(id)

    suspend fun retrySyncRealm(id: Long, isSent: Boolean) = preOrderRealm.updateIsSent(id, isSent)

}