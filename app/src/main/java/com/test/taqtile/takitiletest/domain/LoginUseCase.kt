package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.data.account.AccountRepository
import com.test.taqtile.takitiletest.models.LoginCredentials
import com.test.taqtile.takitiletest.models.LoginResponse
import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
        private val accountRepository: AccountRepository
) {
  fun execute(loginCredentials: LoginCredentials): Observable<LoginResponse> {
    return accountRepository.login(loginCredentials)
  }
}