package com.test.taqtile.takitiletest.data.respositories

import com.test.taqtile.takitiletest.data.mappers.UserDataEntityMapper
import com.test.taqtile.takitiletest.data.mappers.UserDetailsDataEntityMapper
import com.test.taqtile.takitiletest.data.services.UserServices
import com.test.taqtile.takitiletest.domain.UsersDataStore
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import org.json.JSONObject


class RemoteUserDataStore(private val userServices: UserServices) : UsersDataStore {
  private val userDataMapper = UserDataEntityMapper()
  private val detailsDataMapper = UserDetailsDataEntityMapper()

  override fun getUsers(pagination: JSONObject): Observable<List<UserEntity>> {
    return userServices.listUsers(pagination).map { results ->
      results.users.map { userDataMapper.mapFrom(it) }
    }
  }

  override fun getDetails(userId: Int): Observable<UserDetailsEntity> {
    return userServices.getDetails(userId.toString()).flatMap { result ->
      Observable.just(detailsDataMapper.mapFrom(result))
    }
  }

}