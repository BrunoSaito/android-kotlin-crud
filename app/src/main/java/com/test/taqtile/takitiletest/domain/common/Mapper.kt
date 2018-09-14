package com.test.taqtile.takitiletest.domain.common

import io.reactivex.Single


abstract class Mapper<in E, T> {
  abstract fun mapFrom(from: E): T

//  fun mapOptional(from: Optional<E>): Optional<T> {
//    from.value?.let {
//      return Optional.of(mapFrom(it))
//    } ?: return Optional.empty()
//  }

  fun observable(from: E): Single<T> {
    return Single.fromCallable { mapFrom(from) }
  }

  fun observable(from: List<E>): Single<List<T>> {
    return Single.fromCallable { from.map { mapFrom(it) } }
  }
}