package com.julianvelandia.bizorder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.bizorder.domain.PreOrder
import com.julianvelandia.bizorder.domain.PreOrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateEvent {
    data object Success : CreateEvent()
    data object Error : CreateEvent()
}

data class PreOrderState(
    val isLoading: Boolean = true,
    val data: List<PreOrder> = emptyList(),
    val isError: Boolean = false
)

@HiltViewModel
class PreOrderViewModel @Inject constructor(
    private val preOrdersRepository: PreOrdersRepository
) : ViewModel() {

    var eventFlow = MutableSharedFlow<CreateEvent>()
        private set

    val preOrderState: StateFlow<PreOrderState> = preOrdersRepository.getPreOrders()
        .map { result ->
            result.fold(
                onSuccess = { preOrders ->
                    PreOrderState(isLoading = false, data = preOrders)
                },
                onFailure = {
                    PreOrderState(isLoading = false, isError = true)
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PreOrderState(isLoading = true)
        )

    fun onSavePreOrder(customerName: String, product: String) {
        viewModelScope.launch {
            val result = preOrdersRepository.savePreOrder(
                PreOrder(
                    customerName = customerName,
                    product = product
                )
            )

            if (result.isSuccess) {
                eventFlow.emit(CreateEvent.Success)
            } else {
                eventFlow.emit(CreateEvent.Error)
            }
        }
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            preOrdersRepository.onDelete(id)
        }
    }

    fun onSync(id: Long) {
        viewModelScope.launch {
            preOrdersRepository.retrySync(id)
        }
    }
}