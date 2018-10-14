package com.test.taqtile.takitiletest.presentation.account

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.domain.LoginUseCase
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_login.*
import widgets.components.CustomSnackBarBuilder
import javax.inject.Inject

class UserLoginActivity : AppCompatActivity() {

  @Inject
  lateinit var loginUseCase: LoginUseCase

  private var disposables: MutableList<Disposable> = mutableListOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AndroidInjection.inject(this)
    setContentView(R.layout.activity_user_login)

    buttonLogin.setOnClickListener {
      buttonLogin.lockButton()
      this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
      login()
    }
  }

  private fun login() {
    disposables.add(
      loginUseCase.execute(activity_main_login_form.getLoginCredentials())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe ({
                onLoginSuccess()
              }, {
                onLoginFailure(it.message)
              })
    )
  }

  private fun onLoginSuccess() {
    buttonLogin.unlockButton()
    val nextActivity = Intent(this@UserLoginActivity, UserListActivity::class.java)

    startActivity(nextActivity)
    finish()
  }

  private fun onLoginFailure(message: String?) {
    CustomSnackBarBuilder(this@UserLoginActivity)
            .withText(message)
            .show()
    buttonLogin.unlockButton()
  }
}