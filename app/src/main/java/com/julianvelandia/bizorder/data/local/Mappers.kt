package com.julianvelandia.bizorder.data.local

import com.julianvelandia.bizorder.data.local.realm.OrderObject
import com.julianvelandia.bizorder.data.local.realm.PreOrderObject
import com.julianvelandia.bizorder.data.local.room.OrderEntity
import com.julianvelandia.bizorder.data.local.room.PreOrderEntity
import com.julianvelandia.bizorder.domain.Order
import com.julianvelandia.bizorder.domain.PreOrder


fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        customerName = customerName,
        item = item,
        total = total,
        imageUrl = imageUrl
    )
}

fun OrderEntity.toDomain(): Order {
    return Order(
        id = id,
        customerName = customerName,
        item = item,
        total = total,
        imageUrl = imageUrl
    )
}

fun PreOrderEntity.toDomain(): PreOrder {
    return PreOrder(
        id = id,
        customerName,
        product = item,
        isSent = isSent
    )
}

//Realm
fun OrderObject.toDomain(): Order {
    return Order(
        id = id,
        customerName = customerName,
        item = item,
        total = total,
        imageUrl = imageUrl
    )
}


fun Order.toRealmObject(): OrderObject {
    return OrderObject().apply {
        id = this@toRealmObject.id
        customerName = this@toRealmObject.customerName
        item = this@toRealmObject.item
        total = this@toRealmObject.total
        imageUrl = this@toRealmObject.imageUrl
    }
}

fun PreOrderObject.toDomain(): PreOrder {
    return PreOrder(
        id = id,
        customerName = this.customerName,
        product = item,
        isSent = isSent
    )
}