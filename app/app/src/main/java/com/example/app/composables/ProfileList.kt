package com.example.app.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.RetrofitInstance
import com.example.app.models.ProfileListData
import com.example.app.models.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun ProfileList(
  navController: NavHostController
) {
  var data by remember { mutableStateOf(mutableStateListOf<ProfileListData>()) }

  GlobalScope.launch(Dispatchers.IO) {
    val response: Call<List<User>> = RetrofitInstance.api.getUsersList()

    response.enqueue(object: Callback<List<User>> {
      override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

        if (response.isSuccessful && response.body() != null) {
          val res = response.body()

          if (res != null) {
            val tempData = mutableStateListOf<ProfileListData>()
            for (x in res) {
              val firstName = x.first_name
              val lastName = x.last_name
              val fullName = "$firstName $lastName"
              val id = x.id.toString()

              tempData.add(ProfileListData(id, fullName))
            }
            data = tempData.toMutableStateList()
          }
        }
      }

      override fun onFailure(call: Call<List<User>>, t: Throwable) {
        Log.d("FAILURE", "FAILURE: Error Loading UserProfiles", t.cause)
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
        navController.navigate("login")
      },
      modifier = Modifier.align(Alignment.End)
    ) {
      Text(
        text="Logout"
      )
    }

    LazyColumn {
      items(data) { index: ProfileListData ->
        IconButton(
          onClick = {
            navController.navigate("profile/{id}".replace("{id}", index.id))
          },
          modifier = Modifier.fillParentMaxWidth()
        ) {
          Row(
            modifier = Modifier.fillParentMaxWidth()
          ) {
            Text(
              text = index.fullName,
              fontSize = 16.sp,
              textAlign = TextAlign.Left,
              modifier = Modifier.weight(1f)
            )
            Icon(
              Icons.Outlined.ArrowForwardIos,
              contentDescription = "Open Profile",
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Divider(
          color = Color.LightGray,
          thickness = 1.dp
        )
      }
    }
  }
}
