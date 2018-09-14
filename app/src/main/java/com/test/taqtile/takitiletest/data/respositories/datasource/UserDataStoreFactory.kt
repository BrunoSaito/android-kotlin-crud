package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.respositories.UserDataStore


class UserDataStoreFactory(private val userRemoteDataStore: UserRemoteDataStore) {
  fun getRemoteDataStore(): UserDataStore {
    return userRemoteDataStore
  }
}