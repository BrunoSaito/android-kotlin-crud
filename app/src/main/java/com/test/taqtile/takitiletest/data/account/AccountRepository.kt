package com.test.taqtile.takitiletest.data.account

import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.models.*
import io.reactivex.Observable
import org.json.JSONObject
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountRemoteDataSource: AccountRemoteDataSource) {

  fun create(user: UserCreate): Observable<UserResponse> {
    return accountRemoteDataSource.create(user)
  }

  fun list(pagination: JSONObject): Observable<ListUserResponse> {
    return accountRemoteDataSource.list(pagination)
  }

  fun getUserDetails(id: String): Observable<UserResponse> {
    return accountRemoteDataSource.getDetails(id)
  }

  fun login(loginCredentials: LoginCredentials): Observable<LoginResponse> {
    return accountRemoteDataSource.login(loginCredentials)
            .doOnNext {
              Toolbox.userSharedPreferences.saveAccessToken(it.data.token)
            }
  }

//  fun update(request: ): Observable<> {
//    return accountRemoteDataSource.update(request)
//  }
//
  fun delete(id: String): Observable<UserResponse> {
    return accountRemoteDataSource.delete(id)
  }
}