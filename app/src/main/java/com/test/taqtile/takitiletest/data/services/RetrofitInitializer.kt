package com.test.taqtile.takitiletest.data.services

import com.test.taqtile.takitiletest.Preferences.Factory.token
import com.test.taqtile.takitiletest.providers.config.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitializer(token: String?) {

//  private val client: OkHttpClient
//  private val retrofit: Retrofit

//  init {
//    this.client = OkHttpClient().newBuilder()
//                                .addInterceptor(accessTokenProvidingInterceptor(token))
//                                .build()
//
//    this.retrofit = Retrofit.Builder()
//                            .baseUrl(Constants.API_ROOT_HTTP_URL)
//                            .client(client)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//  }

  private fun getClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
            .addInterceptor(accessTokenProvidingInterceptor(token))
            .build()
  }

  fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl(Constants.API_ROOT_HTTP_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
  }

  private fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
            .addHeader("Authorization", token!!)
            .build())
  }

//  fun userServices() = retrofit.create(UserServices::class.java)
}