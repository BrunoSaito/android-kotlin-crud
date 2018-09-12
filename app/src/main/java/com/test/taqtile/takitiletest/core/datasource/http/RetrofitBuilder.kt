package com.test.taqtile.takitiletest.core.datasource.http

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder<T> (val serviceClass: Class<T>,
                          val httpUrl: String,
                          val interceptor: Interceptor? = null) {

  fun build(): Observable<T> {
    return okHttpClient
            .map { retrofitBuilder.client(it) }
            .map(Retrofit.Builder::build)
            .map { it.create(serviceClass) }
  }

  private val retrofitBuilder: Retrofit.Builder
    get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(httpUrl)

  private val okHttpClient: Observable<OkHttpClient>
    get() = Observable.just(OkHttpClient.Builder())
            .map { it.addInterceptor() }
            .map { it.build() }

  private fun OkHttpClient.Builder.addInterceptor(): OkHttpClient.Builder {
    this.addInterceptor(interceptor)

    return this
  }
}