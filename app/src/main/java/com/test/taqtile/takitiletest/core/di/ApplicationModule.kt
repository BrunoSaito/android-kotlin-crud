package com.test.taqtile.takitiletest.core.di

import android.app.Application
import android.content.Context
import com.test.taqtile.takitiletest.OnboardApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class ApplicationModule {

  @Binds
  internal abstract fun application(application: OnboardApplication): Application

  @Module
  companion object {

    @Provides
    @JvmStatic
    fun context(application: Application): Context {
      return application.applicationContext
    }

  }
}