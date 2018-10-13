package com.test.taqtile.takitiletest.core.datasource.http

import com.test.taqtile.takitiletest.data.CredentialsInterceptor
import io.reactivex.Observable

object ServiceFactory {
  fun <T> getObservable(classService: Class<T>,
                       httpUrl: String,
                       interceptor: CredentialsInterceptor? = null) : Observable<T> {
    return RetrofitBuilder(classService, httpUrl, interceptor).build()
  }
}