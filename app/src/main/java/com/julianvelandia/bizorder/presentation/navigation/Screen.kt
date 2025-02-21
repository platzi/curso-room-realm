package com.julianvelandia.bizorder.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Create : Screen("create", "Crear", Icons.Default.Add)
    data object Home : Screen("home", "Ordenes", Icons.Default.Home)
    data object PreOrders : Screen("list", "Pre-ordenes", Icons.AutoMirrored.Filled.List)
    data object DetailOrder : Screen("details/{order_id}", "Detalle", Icons.AutoMirrored.Filled.List) {
        fun getDetailsRoute(orderId: String) = "details/$orderId"
        const val ARG_ORDER_ID = "order_id"
    }
}