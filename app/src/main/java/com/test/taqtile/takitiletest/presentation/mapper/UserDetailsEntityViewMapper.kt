package com.test.taqtile.takitiletest.presentation.mapper

import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.presentation.model.UserDetailsView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDetailsEntityViewMapper @Inject constructor(): Mapper<UserDetailsEntity, UserDetailsView>() {

  override fun mapFrom(from: UserDetailsEntity): UserDetailsView {
    val details = UserDetailsView()

    from.data?.let {
      val data = UserDetailsView.Data(
              it.id,
              it.active,
              it.email,
              it.createdAt,
              it.updatedAt,
              it.name,
              it.role
      )
      details.data = data
    }

    return details
  }
}