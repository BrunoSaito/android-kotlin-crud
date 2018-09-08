package com.test.taqtile.takitiletest

import com.test.taqtile.takitiletest.DataModels.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailsService {
  @GET("/users/{id}")
  fun getUserDetails(@Path("id") id: String?) : Call<UserDetails>
}