package com.test.taqtile.takitiletest.mobileUI.userDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.mobileUI.mapper.UserDetailsViewViewModelMapper
import com.test.taqtile.takitiletest.presentation.userDetails.UserDetailsContract
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.user_details.*
import javax.inject.Inject


class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {

  @Inject lateinit var detailsPresenter: UserDetailsContract.Presenter
  @Inject lateinit var userDetailsAdapter: UserDetailsAdapter
  @Inject lateinit var mapper: UserDetailsViewViewModelMapper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_details)

    AndroidInjection.inject(this)
    setupUserDetailsRecycler()
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

  override fun showDetails(userName: String?, userEmail: String?, userRole: String?) {
    userDetailsName.text = userName
    userDetailsEmail.text = userEmail
    userDetailsRole.text = userRole
  }

  fun setupUserDetailsRecycler() {
    recyclerUserDetails.layoutManager = LinearLayoutManager(this)
    recyclerUserDetails.adapter = userDetailsAdapter
  }
}
