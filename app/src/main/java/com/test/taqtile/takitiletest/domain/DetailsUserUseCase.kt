package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.data.account.AccountRepository
import com.test.taqtile.takitiletest.models.UserResponse
import io.reactivex.Observable
import javax.inject.Inject

class DetailsUserUseCase @Inject constructor(
        private val accountRepository: AccountRepository
) {
  fun execute(id: String): Observable<UserResponse> {
    return accountRepository.getUserDetails(id)
  }
}