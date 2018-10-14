package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.data.account.AccountRepository
import com.test.taqtile.takitiletest.models.UserCreate
import com.test.taqtile.takitiletest.models.UserResponse
import io.reactivex.Observable
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
        private val accountRepository: AccountRepository
) {
  fun execute(user: UserCreate): Observable<UserResponse> {
    return accountRepository.create(user)
  }
}