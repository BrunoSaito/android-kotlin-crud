package com.test.taqtile.takitiletest.domain.usecases

import com.test.taqtile.takitiletest.domain.UsersRepository
import com.test.taqtile.takitiletest.domain.common.Transformer
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import io.reactivex.Observable


class GetUserDetails(transformer: Transformer<UserDetailsEntity>,
                     private val usersRepository: UsersRepository) : UseCase<UserDetailsEntity>(transformer) {

  fun getDetails(userId: Int) : Observable<UserDetailsEntity> {
    val data = HashMap<String, Int>()
    data["param"] = userId

    return observable(data)
  }

  override fun createObservable(data: Map<String, Any>?): Observable<UserDetailsEntity> {
    val userId = data?.get("param")

    userId?.let {
      return usersRepository.getUserDetails(it as Int)
    } ?: return Observable.error{ IllegalArgumentException("User ID must be provided.") }
  }
}