package com.test.taqtile.takitiletest.mobileUI

import com.test.taqtile.takitiletest.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class UiThread @Inject internal constructor() : PostExecutionThread {

  override val scheduler: Scheduler
    get() = AndroidSchedulers.mainThread()

}