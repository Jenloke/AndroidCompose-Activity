package com.example.app.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.RetrofitInstance
import com.example.app.models.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(
  navController: NavHostController
) {
  var inputEmail by remember { mutableStateOf( "") }
  var inputPassword by remember { mutableStateOf( "") }

  // For SnackBar
  val scope = rememberCoroutineScope()
  val snackBarHostState = remember { SnackbarHostState() }

  Scaffold(
    snackbarHost = {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart)
      {
          SnackbarHost(hostState = snackBarHostState)
      }
    }
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(20.dp)
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        TextField(
          value = inputEmail,
          onValueChange = { inputEmail = it },
          placeholder = { Text(text = "Email") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(
          modifier = Modifier.padding(5.dp)
        )

        var passwordVisible by remember { mutableStateOf(false) }
        TextField(
          value = inputPassword,
          onValueChange = { inputPassword = it },
          singleLine = true,
          placeholder = { Text("Password") },
          visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          trailingIcon = {
            val image = if (passwordVisible)
              Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = {
              passwordVisible = !passwordVisible
            }
            ) {
              Icon(imageVector = image, description)
            }
          },
          modifier = Modifier.fillMaxWidth()
        )


        Spacer(
          modifier = Modifier.padding(5.dp)
        )

        FilledTonalButton(onClick = {
          val inputs: HashMap<String, String> = HashMap()
          inputs["email"] = inputEmail
          inputs["password"] = inputPassword
          loginUser(inputs) {
            if (it.code() == 200) {
              navController.navigate("profileList")
            } else if (it.code() == 404) {
              scope.launch {
                snackBarHostState.showSnackbar("User Not Found")
              }
            } else {
              scope.launch {
                snackBarHostState.showSnackbar("Error No Wifi Connection")
              }
            }
          }
        }) {
          Text(text = "Login")
        }
      }
    }
  }
}

@OptIn(DelicateCoroutinesApi::class)
fun loginUser(inputs: HashMap<String, String>, responseAnswer: (Response<User>) -> Unit) {
  GlobalScope.launch(Dispatchers.IO) {
    val response: Call<User> = RetrofitInstance.api.loginUser(inputs)

    response.enqueue(object: Callback<User> {
      override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.code() == 200) {
          responseAnswer.invoke(response)
        }
        if (response.code() == 404) {
          responseAnswer.invoke(response)
        }
      }

      override fun onFailure(call: Call<User>, t: Throwable) {
        Log.d("FAILURE", "FAILURE: Login Error", t.cause)
      }
    })
  }
}