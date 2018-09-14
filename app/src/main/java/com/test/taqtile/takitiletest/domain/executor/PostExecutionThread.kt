package com.test.taqtile.takitiletest.domain.executor

import io.reactivex.Scheduler


interface PostExecutionThread {
  val scheduler: Scheduler
}