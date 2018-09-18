package com.test.taqtile.takitiletest.data.respositories

import com.test.taqtile.takitiletest.data.mappers.UserDetailsDataEntityMapper
import com.test.taqtile.takitiletest.data.respositories.datasource.UserDataStoreFactory
import com.test.taqtile.takitiletest.domain.UserRepository
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject


class UserDataRepository @Inject constructor(private val factory: UserDataStoreFactory,
                                             private val mapper: UserDetailsDataEntityMapper) :
        UserRepository {

  override fun getList(pagination: JSONObject): Single<List<UserEntity>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDetails(userId: String): Single<UserDetailsEntity> {
    val dataStore = factory.getRemoteDataStore()

    return dataStore.getDetails(userId)
            .map { mapper.mapFrom(it) }
  }
}