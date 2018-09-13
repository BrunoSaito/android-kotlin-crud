package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.data.services.ServiceGenerator
import io.reactivex.Observable


class UserDetailsCloudDataStore: UserDetailsDataStore {
  override fun getUserDetails(userId: String): Observable<UserDetailsData> {
    return ServiceGenerator().getUserServices().getDetails(userId).doOnNext {  }
  }
}