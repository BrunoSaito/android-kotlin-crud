package com.test.taqtile.takitiletest.domain.usecases

import com.test.taqtile.takitiletest.domain.UsersRepository
import com.test.taqtile.takitiletest.domain.common.Transformer
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.executor.PostExecutionThread
import com.test.taqtile.takitiletest.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import kotlin.properties.Delegates.observable


//class GetUserDetails(transformer: Transformer<UserDetailsEntity>,
//                     private val usersRepository: UsersRepository) : UseCase<UserDetailsEntity>(transformer) {
//
//  fun getDetails(userId: Int) : Observable<UserDetailsEntity> {
//    val data = HashMap<String, Int>()
//    data["param"] = userId
//
//    return observable(data)
//  }
//
//  override fun createObservable(data: Map<String, Any>?): Observable<UserDetailsEntity> {
//    val userId = data?.get("param")
//
//    userId?.let {
//      return usersRepository.getDetails(it as Int)
//    } ?: return Observable.error{ IllegalArgumentException("User ID must be provided.") }
//  }
//}

open class GetUserDetails @Inject constructor(val usersRepository: UsersRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread,
                                              val userId: String):
        SingleUseCase<UserDetailsEntity, Void?>(threadExecutor, postExecutionThread) {

  override fun buildUseCaseObservable(params: Void?): Single<UserDetailsEntity> {
    return usersRepository.getDetails(userId)
  }
}