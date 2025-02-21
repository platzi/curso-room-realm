package com.julianvelandia.bizorder.data.remote

import com.julianvelandia.bizorder.data.OrderDto
import com.julianvelandia.bizorder.domain.Order

fun OrderDto.toDomain(): Order {
    return Order(
        id = id,
        customerName = customerName,
        item = item,
        total = total,
        imageUrl = imageUrl
    )
}