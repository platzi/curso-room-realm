package com.julianvelandia.bizorder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.bizorder.domain.PreOrder
import com.julianvelandia.bizorder.domain.PreOrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateEvent {
    data object Success : CreateEvent()
    data object Error : CreateEvent()
}

@HiltViewModel
class PreOrderViewModel @Inject constructor(
    private val preOrdersRepository: PreOrdersRepository
) : ViewModel() {

    var eventFlow = MutableSharedFlow<CreateEvent>()
        private set

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
}