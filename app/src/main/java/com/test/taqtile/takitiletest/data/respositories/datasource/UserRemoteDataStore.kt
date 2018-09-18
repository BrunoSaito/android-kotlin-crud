package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.data.respositories.UserDataStore
import com.test.taqtile.takitiletest.data.respositories.UserRemote
import io.reactivex.Single
import javax.inject.Inject


class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote): UserDataStore {

  override fun getDetails(userId: String): Single<UserDetailsData> {
    return userRemote.getDetails(userId)
  }
}