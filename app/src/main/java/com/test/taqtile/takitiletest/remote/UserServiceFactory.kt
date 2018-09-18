package com.test.taqtile.takitiletest.remote

import com.test.taqtile.takitiletest.Preferences
import com.test.taqtile.takitiletest.providers.config.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object UserServiceFactory {

  fun makeUserService(): UserService {
    val okHttpClient = makeOkHttpClient(accessTokenProvidingInterceptor(Preferences.token))
    val retrofit = Retrofit.Builder()
                                    .baseUrl(Constants.API_ROOT_HTTP_URL)
                                    .client(okHttpClient)
                                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
    return retrofit.create(UserService::class.java)
  }

  private fun makeOkHttpClient(intercept: Interceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
                         .addInterceptor(intercept)
                         .build()
  }

  private fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
         .addHeader("Authorization", token!!)
         .build())
  }
}