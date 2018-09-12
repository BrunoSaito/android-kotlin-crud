package com.test.taqtile.takitiletest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitializer(token: String?) {
  private val BASE_URL = "https://tq-template-server-sample.herokuapp.com/"

  private val client: OkHttpClient
  private val retrofit: Retrofit

  init {
    this.client = OkHttpClient().newBuilder()
                                .addInterceptor(accessTokenProvidingInterceptor(token))
                                .build()

    this.retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
  }

  private fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
            .addHeader("Authorization", token!!)
            .build())
  }

  fun userServices() = retrofit.create(UserServices::class.java)
}