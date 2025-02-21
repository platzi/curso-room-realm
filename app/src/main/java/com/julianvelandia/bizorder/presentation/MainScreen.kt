package com.julianvelandia.bizorder.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.julianvelandia.bizorder.presentation.navigation.BottomNavigationBar
import com.julianvelandia.bizorder.presentation.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(Screen.Create.route) {  }
            composable(Screen.Home.route) {
                HomeScreen(modifier = Modifier.padding(innerPadding),)
            }
            composable(Screen.PreOrders.route) {}
        }
    }
}