package com.test.taqtile.takitiletest.mobileUI.mapper

import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.mobileUI.model.UserDetailsViewModel
import com.test.taqtile.takitiletest.presentation.model.UserDetailsView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDetailsViewViewModelMapper @Inject constructor(): Mapper<UserDetailsView, UserDetailsViewModel>() {

  override fun mapFrom(from: UserDetailsView): UserDetailsViewModel {
    val details = UserDetailsViewModel()

    from.data?.let {
      val data = UserDetailsViewModel.Data(
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