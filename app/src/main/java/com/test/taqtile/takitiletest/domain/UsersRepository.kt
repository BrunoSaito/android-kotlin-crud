package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject

interface UsersRepository {
  fun getList(pagination: JSONObject): Single<List<UserEntity>>

  fun getDetails(userId: String): Single<UserDetailsEntity>
}