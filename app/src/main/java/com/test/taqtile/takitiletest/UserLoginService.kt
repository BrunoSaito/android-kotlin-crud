package com.test.taqtile.takitiletest

import com.test.taqtile.takitiletest.DataModels.UserLoginCredentials
import com.test.taqtile.takitiletest.DataModels.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserLoginService {
  @POST("/authenticate")
  fun loginUser(@Body useLoginCredentials: UserLoginCredentials) : Call<UserLoginResponse>
}