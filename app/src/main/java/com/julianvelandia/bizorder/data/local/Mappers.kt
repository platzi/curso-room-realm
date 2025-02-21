package com.julianvelandia.bizorder.data.local

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