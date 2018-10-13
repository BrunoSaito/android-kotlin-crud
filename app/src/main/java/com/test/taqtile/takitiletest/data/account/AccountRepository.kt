package com.test.taqtile.takitiletest.data.account

import android.util.Log
import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.models.LoginCredentials
import com.test.taqtile.takitiletest.models.LoginResponse
import io.reactivex.Observable
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountRemoteDataSource: AccountRemoteDataSource) {

//  fun create(request: ): Observable<> {
//    return accountRemoteDataSource.create(request)
//  }
//
//  fun list(request: ): Observable<> {
//    return accountRemoteDataSource.list(request)
//  }
//
//  fun getUserDetails(): Observable<> {
//    return accountRemoteDataSource.getUserDetails()
//  }

  fun login(loginCredentials: LoginCredentials): Observable<LoginResponse> {
    return accountRemoteDataSource.login(loginCredentials)
            .doOnNext {
              Log.d("D", "repository")
              Toolbox.userSharedPreferences.saveAccessToken(it.data.token)
            }
  }

//  fun update(request: ): Observable<> {
//    return accountRemoteDataSource.update(request)
//  }
//
//  fun delete(request: ): Observable<> {
//    return accountRemoteDataSource.delete(request)
//  }
}