package com.test.taqtile.takitiletest.data.account

import android.util.Log
import com.test.taqtile.takitiletest.core.config.Constants
import com.test.taqtile.takitiletest.core.datasource.http.ServiceFactory
import com.test.taqtile.takitiletest.data.CredentialsInterceptor
import com.test.taqtile.takitiletest.data.validateAndConvertToObservable
import com.test.taqtile.takitiletest.models.LoginCredentials
import com.test.taqtile.takitiletest.models.LoginResponse
import io.reactivex.Observable
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(interceptor: CredentialsInterceptor) {
  private var services: Observable<AccountServices> = ServiceFactory.getObservable(AccountServices::class.java, Constants.API_ROOT_HTTP_URL, interceptor)

//  fun create(request: ): Observable<> {
//    return services
//            .flatMap { it.create() }
//  }

  fun login(loginCredentials: LoginCredentials): Observable<LoginResponse> {
    return services
            .flatMap {
              Log.d("D", "remote data source")
              it.login(loginCredentials)
            }
            .validateAndConvertToObservable()
  }
}