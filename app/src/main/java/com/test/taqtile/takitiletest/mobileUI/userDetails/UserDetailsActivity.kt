package com.test.taqtile.takitiletest.mobileUI.userDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.mobileUI.mapper.UserDetailsViewViewModelMapper
import com.test.taqtile.takitiletest.presentation.userDetails.UserDetailsContract
import kotlinx.android.synthetic.main.activity_user_details.*
import javax.inject.Inject
import dagger.android.AndroidInjector


class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {

  @Inject lateinit var detailsPresenter: UserDetailsContract.Presenter
  @Inject lateinit var detailsAdapter: UserDetailsAdapter
  @Inject lateinit var mapper: UserDetailsViewViewModelMapper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_details)
    AndroidInjection.inject(this)
  }

  override fun setPresenter(presenter: UserDetailsContract.Presenter) {
    detailsPresenter = presenter
  }

  override fun showError(message: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun hideError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showProgressBar() {
    progressBarDetailsUser.visibility = ProgressBar.VISIBLE
  }

  override fun hideProgressBar() {
    progressBarDetailsUser.visibility = ProgressBar.GONE
  }

  override fun setUserName(userName: String?) {
    userDetailName.text = userName
  }

  override fun setUserEmail(userEmail: String?) {
    userDetailEmail.text = userEmail
  }

  override fun setUserRole(userRole: String?) {
    userDetailRole.text = userRole
  }
}
