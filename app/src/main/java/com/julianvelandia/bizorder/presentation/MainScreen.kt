package com.julianvelandia.bizorder.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.julianvelandia.bizorder.presentation.navigation.BottomNavigationBar
import com.julianvelandia.bizorder.presentation.navigation.Screen
import com.julianvelandia.bizorder.presentation.navigation.Screen.DetailOrder.ARG_ORDER_ID

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(Screen.Create.route) {
                CreateScreen()
            }
            composable(Screen.Home.route) {
                HomeScreen(modifier = Modifier.padding(innerPadding), navigateTo = { route ->
                    navController.navigate(Screen.DetailOrder.getDetailsRoute(route))
                })
            }
            composable(Screen.PreOrders.route) {
                PreOrdersScreen(modifier = Modifier.padding(innerPadding))
            }

            composable(
                route = Screen.DetailOrder.route,
                arguments = listOf(
                    navArgument(ARG_ORDER_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                DetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}