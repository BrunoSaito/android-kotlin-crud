package com.test.taqtile.takitiletest

import android.app.Activity
import android.app.Application
import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.core.di.DaggerApplicationComponent
import com.test.taqtile.takitiletest.core.sharedPreferences.UserSharedPreferences
import com.test.taqtile.takitiletest.data.ResponseValidator
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import okhttp3.internal.Internal.instance
import javax.inject.Inject

class OnboardApplication: DaggerApplication() {
//class OnboardApplication : Application(), HasActivityInjector {

  private var instance: OnboardApplication? = null

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.builder().create(this)
  }

  override fun onCreate() {
    super.onCreate()

    instance = this

    setupToolbox()
  }

  private fun setupToolbox() {
    Toolbox.userSharedPreferences = UserSharedPreferences(this)
    Toolbox.responseValidator = ResponseValidator(this)
  }

//  @Inject
//  lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

//  override fun onCreate() {
//    super.onCreate()
//    DaggerApplicationComponent
//            .builder()
//            .application(this)
//            .build()
//            .inject(this)
//  }
//
//  override fun activityInjector(): AndroidInjector<Activity> {
//    return activityDispatchingAndroidInjector
//  }
}