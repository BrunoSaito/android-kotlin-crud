package com.test.taqtile.takitiletest.data

import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class CredentialsInterceptor @Inject constructor(): BaseInterceptor() {

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val request: Request = originalRequest.newBuilder().headers(buildHeaders(originalRequest)).build()
    return chain.proceed(request)
  }

  private fun accessTokenProvidingInterceptor(token: String?) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
            .addHeader("Authorization", token!!)
            .build())
  }
}