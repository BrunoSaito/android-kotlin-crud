package com.test.taqtile.takitiletest.core.di

import android.app.Application
import com.test.taqtile.takitiletest.OnboardApplication
import com.test.taqtile.takitiletest.presentation.account.UserBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = arrayOf(
        ApplicationModule::class,
        AndroidActivityBindingModule::class,
        UserBindingModule::class,
        AndroidSupportInjectionModule::class)
)

//interface ApplicationComponent {
//  @Component.Builder
//  interface Builder {
//    @BindsInstance fun application(application: Application): Builder
//    fun build(): ApplicationComponent
//  }
//
//  fun inject(app: OnboardApplication)
//}

interface ApplicationComponent : AndroidInjector<OnboardApplication> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<OnboardApplication>()

}