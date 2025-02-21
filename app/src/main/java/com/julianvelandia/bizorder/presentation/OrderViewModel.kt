package com.julianvelandia.bizorder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.bizorder.domain.Order
import com.julianvelandia.bizorder.domain.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class HomeState(
    val isLoading: Boolean = true,
    val data: List<Order> = emptyList(),
    val isError: Boolean = false
)

@HiltViewModel
class OrderViewModel  @Inject constructor(
    ordersRepository: OrdersRepository
) : ViewModel() {

    val homeState: StateFlow<HomeState> = ordersRepository.getOrder()
        .distinctUntilChanged()
        .map { result ->
            result.fold(
                onSuccess = { data ->
                    HomeState(
                        isLoading = false,
                        data = data,
                        isError = false
                    )
                },
                onFailure = {
                    HomeState(
                        isLoading = false,
                        data = emptyList(),
                        isError = true
                    )
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeState(isLoading = true)
        )
}