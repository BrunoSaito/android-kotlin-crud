package com.test.taqtile.takitiletest.mobileUI.injection.module

import com.test.taqtile.takitiletest.mobileUI.injection.scopes.PerActivity
import com.test.taqtile.takitiletest.mobileUI.userDetails.UserDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

  @PerActivity
  @ContributesAndroidInjector(modules = arrayOf(UserDetailsActivityModule::class))
  abstract fun bindUserDetailsActivity(): UserDetailsActivity
}