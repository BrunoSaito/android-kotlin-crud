package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.data.account.AccountRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
        private val accountRepository: AccountRepository
) {
  fun execute(id: String) {

  }
}