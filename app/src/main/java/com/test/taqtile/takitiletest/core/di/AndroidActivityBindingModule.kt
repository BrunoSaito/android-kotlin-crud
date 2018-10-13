package com.test.taqtile.takitiletest.core.di

import com.test.taqtile.takitiletest.presentation.account.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AndroidActivityBindingModule {

  @ContributesAndroidInjector
  @ActivityScope
  internal abstract fun userDetailsActivity(): UserDetailsActivity

  @ContributesAndroidInjector
  @ActivityScope
  internal abstract fun userEditActivity(): UserEditActivity

  @ContributesAndroidInjector
  @ActivityScope
  internal abstract fun userFormActivity(): UserFormActivity

  @ContributesAndroidInjector
  @ActivityScope
  internal abstract fun userListActivity(): UserListActivity

  @ContributesAndroidInjector
  @ActivityScope
  internal abstract fun userLoginActivity(): UserLoginActivity
}
