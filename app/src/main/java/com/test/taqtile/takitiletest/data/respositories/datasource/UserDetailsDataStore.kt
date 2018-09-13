package com.test.taqtile.takitiletest.data.respositories.datasource

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import io.reactivex.Observable


interface UserDetailsDataStore {
  fun getUserDetails(userId: String) : Observable<UserDetailsData>
}