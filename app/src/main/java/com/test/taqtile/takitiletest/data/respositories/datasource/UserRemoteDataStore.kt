package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.data.respositories.UserDataStore
import com.test.taqtile.takitiletest.data.respositories.UserRemote
import io.reactivex.Observable


class UserRemoteDataStore(private val userRemote: UserRemote): UserDataStore {

  override fun getDetails(userId: String): Observable<UserDetailsData> {
    return userRemote.getDetails(userId)
  }
}