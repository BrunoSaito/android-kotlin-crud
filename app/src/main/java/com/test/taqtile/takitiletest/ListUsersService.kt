package com.test.taqtile.takitiletest

import com.test.taqtile.takitiletest.DataModels.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ListUsersService {
  @GET("/users")
  fun listUsers(@Query("pagination") pagination: JSONObject,
                @Header("Authorization") authorization: String?) : Call<User>
}