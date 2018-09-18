package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.respositories.UserDataStore
import javax.inject.Inject


class UserDataStoreFactory @Inject constructor(private val userRemoteDataStore: UserRemoteDataStore) {
  fun getRemoteDataStore(): UserDataStore {
    return userRemoteDataStore
  }
}