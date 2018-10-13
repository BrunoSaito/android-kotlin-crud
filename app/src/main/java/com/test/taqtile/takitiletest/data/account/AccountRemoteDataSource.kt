package com.test.taqtile.takitiletest.data.account

import android.util.Log
import com.google.gson.JsonObject
import com.test.taqtile.takitiletest.core.config.Constants
import com.test.taqtile.takitiletest.core.datasource.http.ServiceFactory
import com.test.taqtile.takitiletest.data.CredentialsInterceptor
import com.test.taqtile.takitiletest.data.validateAndConvertToObservable
import com.test.taqtile.takitiletest.models.ListUserResponse
import com.test.taqtile.takitiletest.models.LoginCredentials
import com.test.taqtile.takitiletest.models.LoginResponse
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(interceptor: CredentialsInterceptor) {
  private var services: Observable<AccountServices> = ServiceFactory.getObservable(AccountServices::class.java, Constants.API_ROOT_HTTP_URL, interceptor)

//  fun create(request: ): Observable<> {
//    return services
//            .flatMap { it.create() }
//  }

  fun login(loginCredentials: LoginCredentials): Observable<LoginResponse> {
    return services
            .flatMap { it.login(loginCredentials) }
            .validateAndConvertToObservable()
  }

  fun list(pagination: JSONObject): Observable<ListUserResponse> {
    return services
            .flatMap { it.list(pagination) }
            .validateAndConvertToObservable()
  }
}