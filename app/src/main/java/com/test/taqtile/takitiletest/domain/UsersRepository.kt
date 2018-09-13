package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import org.json.JSONObject

interface UsersRepository {
  fun getUsers(pagination: JSONObject): Observable<List<UserEntity>>

  fun getUserDetails(userId: Int): Observable<UserDetailsEntity>
}