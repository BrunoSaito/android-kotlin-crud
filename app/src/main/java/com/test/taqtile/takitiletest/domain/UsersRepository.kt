package com.test.taqtile.takitiletest.domain

import com.test.taqtile.takitiletest.domain.entities.UserEntity
import io.reactivex.Observable
import java.util.*

interface UsersRepository {
  fun getUsers(): Observable<List<UserEntity>>

  fun search(query: String): Observable<List<UserEntity>>

  fun getMovie(movieId: Int): Observable<Optional<UserEntity>>
}