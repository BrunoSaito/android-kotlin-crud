package com.test.taqtile.takitiletest.domain.usecases

import com.test.taqtile.takitiletest.domain.UsersRepository
import com.test.taqtile.takitiletest.domain.common.Transformer
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable


class GetUserList (transformer: Transformer<List<UserEntity>>,
                   private val usersRepository: UsersRepository) : UseCase<List<UserEntity>>(transformer) {

  override fun createObservable(data: Map<String, Any>?): Observable<List<UserEntity>> {
    return usersRepository.getUsers()
  }
}