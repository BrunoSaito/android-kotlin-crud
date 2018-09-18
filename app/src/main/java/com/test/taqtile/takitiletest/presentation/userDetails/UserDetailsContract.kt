package com.test.taqtile.takitiletest.presentation.userDetails

import com.test.taqtile.takitiletest.presentation.BasePresenter
import com.test.taqtile.takitiletest.presentation.BaseView


interface UserDetailsContract {

  interface View: BaseView<Presenter> {
    fun showProgressBar()

    fun hideProgressBar()

    fun showError(message: String?)

    fun hideError()

//    fun setUserName(userName: String?)
//
//    fun setUserEmail(userEmail: String?)
//
//    fun setUserRole(userRole: String?)

    fun showDetails(userName: String?, userEmail: String?, userRole: String?)

  }

  interface Presenter: BasePresenter {
    fun getDetails(userId: String)
  }
}