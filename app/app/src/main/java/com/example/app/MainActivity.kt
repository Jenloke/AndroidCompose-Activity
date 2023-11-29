package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.composables.Login
import com.example.app.composables.Profile
import com.example.app.composables.ProfileList
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Surface(
          modifier = Modifier.fillMaxSize()
        ) {
          val navController = rememberNavController().apply { setViewModelStore(ViewModelStore()) }
          NavHost(
            navController = navController,
            startDestination = "login"
          ) {
            composable("login") {
              Login(
                navController
              )
            }
            composable("profileList") {
              ProfileList(
                navController
              )
            }
            composable("profile/{id}") { navBackStackEntry ->
              val id = navBackStackEntry.arguments?.getString("id")
              Profile(navController, id!!.toInt())
            }
          }
        }
      }
    }
  }
}







