package com.example.whats_app_sample.routing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whats_app_sample.CAViewModel
import com.example.whats_app_sample.LoginScreen
import com.example.whats_app_sample.ui.ChatListScreen
import com.example.whats_app_sample.ui.ProfileScreen
import com.example.whats_app_sample.ui.SignupScreen
import com.example.whats_app_sample.ui.SingleChatScreen
import com.example.whats_app_sample.ui.SingleStatusScreen
import com.example.whats_app_sample.ui.StatusListScreen
import com.example.whats_app_sample.utils.NotificationMessage

sealed class DestinationRouting(val route: String) {
    object Signup : DestinationRouting("signup")
    object Login : DestinationRouting("login")
    object Profile : DestinationRouting("profile")
    object ChatList : DestinationRouting("chatList")
    object SingleChat : DestinationRouting("singleChat/{chatId}") {
        fun createRoute(id: String) =  "sigleChat/$id"
    }
    object StatusList : DestinationRouting("statusList")
    object SingleStatus : DestinationRouting("sigleStatus/{statusId}") {
        fun createRoute(id: String) =  "sigleStatus/$id"
    }
}

@Composable
fun ChatAppNavigation() {
    val navController = rememberNavController()
    val vm = hiltViewModel<CAViewModel>()
    
    NotificationMessage(vm = vm)

    NavHost(navController = navController, startDestination = DestinationRouting.Signup.route) {
        composable(DestinationRouting.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }
        composable(DestinationRouting.Login.route) {
            LoginScreen()
        }
        composable(DestinationRouting.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(DestinationRouting.StatusList.route) {
            StatusListScreen(navController = navController)
        }
        composable(DestinationRouting.SingleStatus.route) {
            SingleStatusScreen(statusId = "123")
        }
        composable(DestinationRouting.ChatList.route) {
            ChatListScreen(navController = navController)
        }
        composable(DestinationRouting.SingleChat.route) {
            SingleChatScreen(chatId = "123")
        }
    }
}