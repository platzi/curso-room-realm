package com.julianvelandia.bizorder.data.remote

import com.julianvelandia.bizorder.data.BizOrderApi
import com.julianvelandia.bizorder.data.OrderDto
import javax.inject.Inject

class RemoteDataStorage @Inject constructor(
    private val bizOrderApi: BizOrderApi
) {
    suspend fun getOrders(): Result<List<OrderDto>> = runCatching {
        val response = bizOrderApi.getOrders()
        if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            throw Exception("${response.code()}")
        }
    }

    suspend fun savePreOrder() = runCatching {
        val response = bizOrderApi.savePreOrder()
        if (response.isSuccessful) {
            response.body() ?: Unit
        } else {
            throw Exception("HTTP ${response.code()}: ${response.message()}")
        }
    }

}