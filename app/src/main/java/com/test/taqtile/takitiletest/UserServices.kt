package com.test.taqtile.takitiletest

import com.test.taqtile.takitiletest.DataModels.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface UserServices {
  @POST("/users")
  fun createNewUser(@Body createNewUserData: CreateNewUserData?) : Call<CreateNewUserSuccess>

  @GET("/users")
  fun listUsers(@Query("pagination") pagination: JSONObject?) : Call<User>

  @GET("/users/{id}")
  fun getUserDetails(@Path("id") id: String?) : Call<UserDetails>

  @POST("/authenticate")
  fun loginUser(@Body userLoginCredentials: UserLoginCredentials?) : Call<UserLoginSuccess>

  @PUT("/users/{id}")
  fun updateUserData(@Path("id") id: String?, @Body editUserData: EditUserData?) : Call<EditUserSuccess>

  @DELETE("/users/{id}")
  fun deleteUser(@Path("id") id: String?) : Call<DeleteUserSuccess>
}