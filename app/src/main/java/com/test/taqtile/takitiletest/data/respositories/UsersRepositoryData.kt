package com.test.taqtile.takitiletest.data.respositories

import com.test.taqtile.takitiletest.domain.UsersRepository
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import org.json.JSONObject


class UsersRepositoryData(private val remoteDataStore: RemoteUserDataStore) : UsersRepository {
  override fun getUsers(pagination: JSONObject): Observable<List<UserEntity>> {
    return remoteDataStore.getUsers(pagination)
  }

  override fun getUserDetails(userId: Int): Observable<UserDetailsEntity> {
    return remoteDataStore.getUserDetails(userId)
  }
}