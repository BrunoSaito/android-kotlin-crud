package com.test.taqtile.takitiletest.data

import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.core.config.Constants
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request

abstract class BaseInterceptor: Interceptor {
  private val JSON_HEADER_KEY: String = "Content-Type"
  private val JSON_HEADER_VALUE: String = "application/json"

  fun buildHeaders(request: Request): Headers {
    val builder: Headers.Builder = request.headers().newBuilder()
    val accessToken: String? = Toolbox.userSharedPreferences.getAccessToken()

    // Content type
    builder.set(JSON_HEADER_KEY, JSON_HEADER_VALUE)
    // App authorization for API V1 requests
    builder.set(Constants.AUTHORIZATION_HEADER, "Bearer $accessToken")

    return builder.build()
  }
}