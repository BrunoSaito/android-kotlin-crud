package com.test.taqtile.takitiletest.data.mappers

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDetailsDataEntityMapper @Inject constructor() : Mapper<UserDetailsData, UserDetailsEntity>() {

  override fun mapFrom(from: UserDetailsData): UserDetailsEntity {
    val details = UserDetailsEntity()

    from.data?.let {
      val dataEntity = UserDetailsEntity.Data(
              it.id,
              it.active,
              it.email,
              it.createdAt,
              it.updatedAt,
              it.name,
              it.role)
      details.data = dataEntity
    }

    return details
  }
}