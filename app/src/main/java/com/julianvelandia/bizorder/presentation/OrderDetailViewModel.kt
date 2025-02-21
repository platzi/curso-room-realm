package com.julianvelandia.bizorder.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.bizorder.domain.Order
import com.julianvelandia.bizorder.domain.OrdersRepository
import com.julianvelandia.bizorder.presentation.navigation.Screen.DetailOrder.ARG_ORDER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailState(
    val isLoading: Boolean = true,
    val data: Order? = null,
    val isError: Boolean = false
)

@HiltViewModel
class OrderDetailViewModel  @Inject constructor(
    ordersRepository: OrdersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String? = savedStateHandle[ARG_ORDER_ID]

    var detailState = MutableStateFlow(DetailState())
        private set

    init {
        viewModelScope.launch {
            orderId?.let { id ->
                ordersRepository.getOrderById(id)
                    .onSuccess { order -> detailState.value = DetailState(isLoading = false, data = order) }
                    .onFailure { detailState.value = DetailState(isLoading = false, isError = true) }
            } ?: run {
                detailState.value = DetailState(isLoading = false, isError = true)
            }
        }
    }


}