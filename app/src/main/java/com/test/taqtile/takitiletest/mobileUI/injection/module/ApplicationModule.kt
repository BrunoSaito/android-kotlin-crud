package com.test.taqtile.takitiletest.mobileUI.injection.module

import android.app.Application
import android.content.Context
import com.test.taqtile.takitiletest.data.executor.JobExecutor
import com.test.taqtile.takitiletest.data.mappers.UserDetailsDataEntityMapper
import com.test.taqtile.takitiletest.data.respositories.UserDataRepository
import com.test.taqtile.takitiletest.data.respositories.UserRemote
import com.test.taqtile.takitiletest.data.respositories.datasource.UserDataStoreFactory
import com.test.taqtile.takitiletest.domain.UserRepository
import com.test.taqtile.takitiletest.domain.executor.PostExecutionThread
import com.test.taqtile.takitiletest.domain.executor.ThreadExecutor
import com.test.taqtile.takitiletest.mobileUI.UiThread
import com.test.taqtile.takitiletest.mobileUI.injection.scopes.PerApplication
import com.test.taqtile.takitiletest.remote.UserRemoteImpl
import com.test.taqtile.takitiletest.remote.UserService
import com.test.taqtile.takitiletest.remote.UserServiceFactory
import com.test.taqtile.takitiletest.remote.mapper.UserDetailsModelDataMapper
import dagger.Module
import dagger.Provides


@Module
open class ApplicationModule {

  @Provides
  @PerApplication
  internal fun provideContext(application: Application): Context {
    return application
  }

  @Provides
  @PerApplication
  internal fun provideUsersRepository(factory: UserDataStoreFactory,
                             mapper: UserDetailsDataEntityMapper): UserRepository {
    return UserDataRepository(factory, mapper)
  }

  @Provides
  @PerApplication
  internal fun providesUsersRemote(service: UserService,
                          mapper: UserDetailsModelDataMapper): UserRemote {
    return UserRemoteImpl(service, mapper)
  }

  @Provides
  @PerApplication
  internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
    return jobExecutor
  }

  @Provides
  @PerApplication
  internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
    return uiThread
  }

  @Provides
  @PerApplication
  internal fun provideUserService(): UserService {
    return UserServiceFactory.makeUserService()
  }
}