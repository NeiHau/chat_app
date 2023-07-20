package com.example.whats_app_sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.whats_app_sample.utils.BottomNavigationItem
import com.example.whats_app_sample.utils.BottomNavigationMenu

@Composable
fun StatusListScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "StatusList Screen")
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.STATUSLIST,
            navController = navController
        )
    }
}