package com.julianvelandia.bizorder.data.local.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRealm @Inject constructor(
    private val realm: Realm
) {

    suspend fun addOrders(orders: List<OrderObject>) {
        realm.write {
            orders.forEach { order ->
                copyToRealm(order, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    fun getAllOrders(): Flow<List<OrderObject>> {
        return realm.asFlow()
            .map { realmChange ->
                realmChange.realm.query(OrderObject::class).find()
            }
    }

    fun getOrderById(orderId: String): OrderObject? {
        return realm.query(OrderObject::class, "id == $0", orderId)
            .first()
            .find()
    }
}