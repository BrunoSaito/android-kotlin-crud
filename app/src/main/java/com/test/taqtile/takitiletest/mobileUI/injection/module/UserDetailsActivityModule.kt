package com.test.taqtile.takitiletest.mobileUI.injection.module

import com.test.taqtile.takitiletest.domain.usecases.GetUserDetails
import com.test.taqtile.takitiletest.mobileUI.injection.scopes.PerActivity
import com.test.taqtile.takitiletest.mobileUI.userDetails.UserDetailsActivity
import com.test.taqtile.takitiletest.presentation.mapper.UserDetailsEntityViewMapper
import com.test.taqtile.takitiletest.presentation.userDetails.UserDetailsContract
import com.test.taqtile.takitiletest.presentation.userDetails.UserDetailsPresenter
import dagger.Module
import dagger.Provides


@Module
open class UserDetailsActivityModule {
  @PerActivity
  @Provides
  internal fun provideUserDetailsView(userDetailsActivity: UserDetailsActivity): UserDetailsContract.View {
    return userDetailsActivity
  }

  @PerActivity
  @Provides
  internal fun provideUserDetailsPresenter(mainView: UserDetailsContract.View,
                                           getUserDetails: GetUserDetails,
                                           mapper: UserDetailsEntityViewMapper) :
          UserDetailsContract.Presenter {
    return UserDetailsPresenter(mainView, getUserDetails, mapper)
  }
}