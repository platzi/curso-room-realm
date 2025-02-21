package com.julianvelandia.bizorder.data.local.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreOrderRealm @Inject constructor(
    private val realm: Realm
) {


    suspend fun insert(preOrder: PreOrderObject) {
        realm.write {
            copyToRealm(preOrder)
        }
    }


    fun getAllPreOrders(): Flow<List<PreOrderObject>> {
        return realm.query<PreOrderObject>().asFlow().map { it.list }
    }

    suspend fun deleteById(preOrderId: Long) {
        realm.write {
            query<PreOrderObject>("id == $0", preOrderId).first().find()?.let { delete(it) }
        }
    }

    suspend fun updateIsSent(preOrderId: Long, isSent: Boolean) {
        realm.write {
            query<PreOrderObject>("id == $0", preOrderId).first().find()?.let { order ->
                order.isSent = isSent
            }
        }
    }
}