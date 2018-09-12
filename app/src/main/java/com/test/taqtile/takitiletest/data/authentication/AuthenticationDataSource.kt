package com.test.taqtile.takitiletest.data.authentication

import com.test.taqtile.takitiletest.core.datasource.http.ServiceFactory
import com.test.taqtile.takitiletest.providers.config.Constants
import io.reactivex.Observable
import okhttp3.Interceptor

class AuthenticationDataSource constructor(interceptor: Interceptor) {
  private var services: Observable<AuthenticationServices> = ServiceFactory.getObservable(AuthenticationServices::class.java, Constants.API_ROOT_HTTP_URL, interceptor = interceptor)

  fun login (request: LoginRequest): Observable<LoginResponse> {

  }
}