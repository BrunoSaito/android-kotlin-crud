package com.test.taqtile.takitiletest.data.authentication

import com.test.taqtile.takitiletest.models.UserLoginCredentials
import com.test.taqtile.takitiletest.models.UserLoginSuccess
import com.test.taqtile.takitiletest.providers.config.Constants
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthenticationServices {
  @POST(Constants.ACCOUNT_LOGIN)
  fun loginUser(@Body userLoginCredentials: UserLoginCredentials?) : Observable<Result<UserLoginSuccess>>
}