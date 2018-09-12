package com.test.taqtile.takitiletest.data.Services

import com.test.taqtile.takitiletest.models.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface UserServices {
  @POST("/users")
  fun createNewUser(@Body createNewUserData: CreateNewUserData?) : Call<CreateNewUserSuccess>

  @GET("/users")
  fun listUsers(@Query("pagination") pagination: JSONObject?) : Call<User>

  @GET("/users/{id}")
  fun getUserDetails(@Path("id") id: String?) : Call<UserDetails>

//  @POST("/authenticate")
//  fun loginUser(@Body userLoginCredentials: UserLoginCredentials?) : Call<UserLoginSuccess>

  @PUT("/users/{id}")
  fun updateUserData(@Path("id") id: String?, @Body editUserData: EditUserData?) : Call<EditUserSuccess>

  @DELETE("/users/{id}")
  fun deleteUser(@Path("id") id: String?) : Call<DeleteUserSuccess>
}