package com.test.taqtile.takitiletest.data.respositories

import com.test.taqtile.takitiletest.data.mappers.UserDetailsDataEntityMapper
import com.test.taqtile.takitiletest.data.respositories.datasource.UserDataStoreFactory
import com.test.taqtile.takitiletest.domain.UsersRepository
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject


//class UsersDataRepository(private val remoteDataStore: RemoteUserDataStore) : UsersRepository {
//  override fun getList(pagination: JSONObject): Observable<List<UserEntity>> {
//    return remoteDataStore.getUsers(pagination)
//  }
//
//  override fun getDetails(userId: Int): Observable<UserDetailsEntity> {
//    return remoteDataStore.getDetails(userId)
//  }
//}

class UsersDataRepository(private val factory: UserDataStoreFactory,
                          private val mapper: UserDetailsDataEntityMapper) : UsersRepository {

  override fun getList(pagination: JSONObject): Single<List<UserEntity>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDetails(userId: String): Single<UserDetailsEntity> {
    val dataStore = factory.getRemoteDataStore()

    return dataStore.getDetails(userId)
            .map { mapper.mapFrom(it) }
  }
}