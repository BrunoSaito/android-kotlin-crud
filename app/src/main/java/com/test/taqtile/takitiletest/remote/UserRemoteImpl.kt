package com.test.taqtile.takitiletest.remote

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.data.respositories.UserRemote
import com.test.taqtile.takitiletest.remote.mapper.UserDetailsModelDataMapper
import io.reactivex.Observable


class UserRemoteImpl(private val userService: UserService,
                     private val mapper: UserDetailsModelDataMapper): UserRemote {

  override fun getDetails(userId: String): Observable<UserDetailsData> {
    return userService.getDetails(userId)
            .map { mapper.mapFrom(it) }
  }
}