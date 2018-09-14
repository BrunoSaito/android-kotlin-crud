package com.test.taqtile.takitiletest.data.respositories

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import io.reactivex.Single


interface UserRemote {
  fun getDetails(userId: String): Single<UserDetailsData>
}