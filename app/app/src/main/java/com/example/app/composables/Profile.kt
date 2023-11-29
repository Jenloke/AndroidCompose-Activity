package com.example.app.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Profile(
  navController: NavHostController,
  id : Int
) {
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var favoriteFood by remember { mutableStateOf("") }

  GlobalScope.launch(Dispatchers.IO) {
    val response: Call<User> = RetrofitInstance.api.getUniqueUser(id)

    response.enqueue(object: Callback<User> {
      override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.isSuccessful && response.body() != null) {
          val res = response.body()

          name = "${res!!.first_name} ${res.last_name}"
          email = res.email
          favoriteFood = res.favorite_food
        }
      }

      override fun onFailure(call: Call<User>, t: Throwable) {
        Log.d("FAILURE", "FAILURE Error: Loading Selected User", t.cause)
      }
    })
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
  ) {
    FilledTonalButton(
      onClick = {
        navController.navigate("profileList")
      },
      modifier = Modifier.align(Alignment.End)
    ) {
      Text(
        text = "Back"
      )
    }

    Text(
      text = "Name: $name",
      fontSize = 14.sp
    )
    Text(
      text = "Email: $email",
      fontSize = 14.sp
    )
    Text(
      text = "Favorite Food: $favoriteFood",
      fontSize = 14.sp
    )
  }
}