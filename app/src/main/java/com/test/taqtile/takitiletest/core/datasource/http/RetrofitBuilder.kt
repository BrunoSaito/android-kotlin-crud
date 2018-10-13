package com.test.taqtile.takitiletest.core.datasource.http

import com.test.taqtile.takitiletest.data.CredentialsInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder<T> (val serviceClass: Class<T>,
                          val httpUrl: String,
                          val interceptor: CredentialsInterceptor? = null) {

  fun build(): Observable<T> {
    return okHttpClient
            .map { retrofitBuilder.client(it) }
            .map(Retrofit.Builder::build)
            .map { it.create(serviceClass) }
  }

  private val retrofitBuilder: Retrofit.Builder
    get() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(httpUrl)

  private val okHttpClient: Observable<OkHttpClient>
    get() = Observable.just(OkHttpClient.Builder())
            .map { it.addInterceptor() }
            .map { it.connectTimeout(20, TimeUnit.SECONDS) }
            .map { it.readTimeout(20, TimeUnit.SECONDS) }
            .map { it.writeTimeout(20, TimeUnit.SECONDS) }
            .map { it.build() }

  private fun OkHttpClient.Builder.addInterceptor(): OkHttpClient.Builder {
    this.addInterceptor(interceptor)
    return this
  }
}