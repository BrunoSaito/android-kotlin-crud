package com.test.taqtile.takitiletest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitializer {
  private val BASE_URL = "https://tq-template-server-sample.herokuapp.com/"

  private val client: OkHttpClient
  private val retrofit: Retrofit

  constructor(token: String?) {
    this.client = OkHttpClient().newBuilder()
                                .addInterceptor(accessTokenProvidingInterceptor(token))
                                .build()

    this.retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

    Log.d("D", "token boladao: " + token)
  }

  fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
            .addHeader("Authorization", token!!)
            .build())
  }

  fun listUsersService() = retrofit.create(ListUsersService::class.java)

  fun userLoginService() = retrofit.create(UserLoginService::class.java)
}