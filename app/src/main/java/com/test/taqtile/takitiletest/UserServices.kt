package com.test.taqtile.takitiletest

import com.test.taqtile.takitiletest.DataModels.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface UserServices {
  @POST("/users")
  fun createNewUser(@Body createNewUserData: CreateNewUserData) : Call<CreateNewUserSuccess>

  @GET("/users")
  fun listUsers(@Query("pagination") pagination: JSONObject) : Call<User>

  @GET("/users/{id}")
  fun getUserDetails(@Path("id") id: String?) : Call<UserDetails>

  @POST("/authenticate")
  fun loginUser(@Body useLoginCredentials: UserLoginCredentials) : Call<UserLoginSuccess>
}