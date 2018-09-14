package com.test.taqtile.takitiletest.remote.mapper

import com.test.taqtile.takitiletest.data.entities.UserDetailsData
import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.remote.model.UserDetailsModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDetailsModelDataMapper @Inject constructor(): Mapper<UserDetailsModel, UserDetailsData>() {

  override fun mapFrom(from: UserDetailsModel): UserDetailsData {
    val details = UserDetailsData()

    from.data?.let {
      val dataData = UserDetailsData.Data(
              it.id,
              it.active,
              it.email,
              it.createdAt,
              it.updatedAt,
              it.name,
              it.role)
      details.data = dataData
    }

    return details
  }
}