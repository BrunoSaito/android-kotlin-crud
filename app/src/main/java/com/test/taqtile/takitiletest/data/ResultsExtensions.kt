package com.test.taqtile.takitiletest.data

import com.test.taqtile.takitiletest.core.Toolbox
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.Result

fun <T> Observable<Result<T>>.validateAndConvertToObservable(): Observable<T> {
  return this.flatMap { Toolbox.responseValidator.convertResultToObservableModel(it) }
          .subscribeOn(Schedulers.io())
}