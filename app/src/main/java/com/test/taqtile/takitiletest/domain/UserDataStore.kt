package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import org.json.JSONObject


interface UserDataStore {
  fun getUsers(pagination: JSONObject): Observable<List<UserEntity>>

  fun getDetails(userId: Int): Observable<UserDetailsEntity>
}