package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.data.account.AccountRepository
import com.test.taqtile.takitiletest.models.ListUserResponse
import io.reactivex.Observable
import org.json.JSONObject
import javax.inject.Inject

class ListUsersUseCase @Inject constructor(
        private val accountRepository: AccountRepository
) {
  fun execute(pagination: JSONObject): Observable<ListUserResponse> {
    return accountRepository.list(pagination)
  }
}