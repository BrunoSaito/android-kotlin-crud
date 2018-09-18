package com.test.taqtile.takitiletest.mobileUI.injection

import android.app.Application
import com.test.taqtile.takitiletest.mobileUI.OnboardApplication
import com.test.taqtile.takitiletest.mobileUI.injection.module.ActivityBindingModule
import com.test.taqtile.takitiletest.mobileUI.injection.module.ApplicationModule
import com.test.taqtile.takitiletest.mobileUI.injection.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule


@PerApplication
@Component(modules = arrayOf(ActivityBindingModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class))
interface ApplicationComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance fun Application(application: Application): Builder
      fun build(): ApplicationComponent
  }

  fun inject(app: OnboardApplication)
}