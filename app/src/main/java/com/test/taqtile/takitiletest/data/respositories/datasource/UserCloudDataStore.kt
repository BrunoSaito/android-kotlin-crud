package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.data.respositories.UserDataStore
import com.test.taqtile.takitiletest.data.services.ServiceGenerator
import io.reactivex.Observable


class UserCloudDataStore: UserDataStore {
  override fun getDetails(userId: String): Observable<UserDetailsData> {
    return ServiceGenerator().getUserServices().getDetails(userId).doOnNext {  }
  }
}