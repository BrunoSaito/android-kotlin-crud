package com.test.taqtile.takitiletest

import android.support.constraint.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL = "https://tq-template-server-sample.herokuapp.com/"

class RetrofitInitializer {
  val client = OkHttpClient().newBuilder()
//          .addInterceptor(TokenRefreshInterceptor(apiKey, cacheDuration))
//          .addInterceptor(HttpLoggingInterceptor().apply {
//            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//          })
          .build()

  val retrofit = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build()

  fun listUsersService() = retrofit.create(ListUsersService::class.java)

  fun userLoginService() = retrofit.create(UserLoginService::class.java)
}