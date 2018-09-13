package com.test.taqtile.takitiletest.data.services

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.models.*
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface UserServices {
  @POST("/users")
  fun createNewUser(@Body createNewUserData: CreateNewUserData?) : Observable<CreateNewUserSuccess>

  @GET("/users")
  fun listUsers(@Query("pagination") pagination: JSONObject?) : Observable<UserListResult>

  @GET("/users/{id}")
  fun getDetails(@Path("id") id: String?) : Observable<UserDetailsData>

  @POST("/authenticate")
  fun loginUser(@Body userLoginCredentials: UserLoginCredentials?) : Observable<UserLoginSuccess>

  @PUT("/users/{id}")
  fun updateUserData(@Path("id") id: String?, @Body editUserData: EditUserData?) : Observable<EditUserSuccess>

  @DELETE("/users/{id}")
  fun deleteUser(@Path("id") id: String?) : Observable<DeleteUserSuccess>
}