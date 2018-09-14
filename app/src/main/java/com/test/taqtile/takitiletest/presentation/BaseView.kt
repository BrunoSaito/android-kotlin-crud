package com.test.taqtile.takitiletest.presentation


interface BaseView<in T : BasePresenter> {

  fun setPresenter(presenter: T)

}