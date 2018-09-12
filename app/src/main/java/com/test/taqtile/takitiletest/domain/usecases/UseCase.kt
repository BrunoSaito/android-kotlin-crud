package com.test.taqtile.takitiletest.domain.usecases

import com.test.taqtile.takitiletest.domain.common.Transformer
import io.reactivex.Observable


abstract class UseCase<T> (private val transformer: Transformer<T>) {

  abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

  fun observable(withData: Map<String, Any>? = null): Observable<T> {
    return createObservable(withData).compose(transformer)
  }
}