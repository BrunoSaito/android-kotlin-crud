package com.test.taqtile.takitiletest.core.datasource.http

import io.reactivex.Observable
import okhttp3.Interceptor

object ServiceFactory {
  fun <T> getObservable(classService: Class<T>,
                       httpUrl: String,
                       interceptor: Interceptor? = null) : Observable<T> {
    return RetrofitBuilder(classService, httpUrl, interceptor).build()
  }
}