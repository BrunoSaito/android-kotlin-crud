package com.test.taqtile.takitiletest.data.mappers

import com.test.taqtile.takitiletest.data.entities.UserData
import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDataEntityMapper @Inject constructor() : Mapper<UserData, UserEntity>() {

  override fun mapFrom(from: UserData): UserEntity {
    val user = UserEntity()

    from.data?.let {
      val data = it.map {
        return@map UserEntity.Data(
                it.id,
                it.active,
                it.email,
                it.createdAt,
                it.updatedAt,
                it.name,
                it.role
        )
      }
      user.data = data
    }

    from.pagination?.let {
      val pagination = UserEntity.Pagination(
              it.page,
              it.window,
              it.total,
              it.totalPages
      )
      user.pagination = pagination
    }

    return user
  }
}