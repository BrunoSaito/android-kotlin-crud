package com.test.taqtile.takitiletest.presentation.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.domain.LoginUseCase
import com.test.taqtile.takitiletest.models.LoginResponse
import com.test.taqtile.takitiletest.services.APIRequest
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_login.*
import javax.inject.Inject

class UserLoginActivity : AppCompatActivity() {

  @Inject
  lateinit var loginUseCase: LoginUseCase

  private var email: String? = null
  private var password: String? = null
  private var disposables: MutableList<Disposable> = mutableListOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AndroidInjection.inject(this)
    setContentView(R.layout.activity_user_login)

    buttonLogin.setOnClickListener {
      buttonLogin.lockButton()
      login()
    }
  }

//  private fun login() {
//    email = editTextLoginEmail.getInputText()
//    password = editTextLoginPassword.getInputText()
//
//    val validEmail = editTextLoginEmail.validate()
//    val validPassword = editTextLoginPassword.validate()
//
//    if (validEmail && validPassword) {
//      buttonLogin.lockButton()
//
//      makeLoginRequest(email, password)
//    }
//  }

  private fun login() {
    disposables.add(
      loginUseCase.execute(activity_main_login_form.getLoginCredentials())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe ({
                onLoginSuccess(it)
              }, {
                onLoginFailed(it.message)
              })
    )
  }

  private fun onLoginSuccess(loginResponse: LoginResponse) {
    Log.d("D", "success : ${loginResponse}")
    Log.d("D", "access token : ${Toolbox.userSharedPreferences.getAccessToken()}")
    buttonLogin.unlockButton()
    val nextActivity = Intent(this@UserLoginActivity, UserListActivity::class.java)

    startActivity(nextActivity)
    finish()
  }

  private fun onLoginFailed(message: String?) {
    Log.d("D", "failed : $message")
    buttonLogin.unlockButton()
  }

//  private fun makeLoginRequest(email: String?, password: String?) {
//    val userLoginRequest = RetrofitInitializer("").userServices().loginUser(UserLoginCredentials(email, password, false))
//
//    userLoginRequest.enqueue(object: Callback<UserLoginSuccess?> {
//      override fun onResponse(call: Call<UserLoginSuccess?>?, response: Response<UserLoginSuccess?>?) {
//        try {
//          val userName = response!!.body()!!.data!!.user!!.name
//          val token = response.body()!!.data!!.token
//
//          Preferences(this@UserLoginActivity).savePreferences(userName, token)
//
//          val nextActivity = Intent(this@UserLoginActivity, LoginSuccessActivity::class.java)
//
//          startActivity(nextActivity)
//          finish()
//        }
//        catch (e: Exception) {
//          val jsonError = response!!.errorBody()!!.string()
//          val userLoginError = Gson().fromJson(jsonError, UserLoginError::class.java)
//
//          val builder = AlertDialog.Builder(this@UserLoginActivity)
//
//          builder.setTitle(getString(R.string.login_failure))
//          builder.setMessage(userLoginError.errors!![0].message)
//          builder.setNeutralButton("OK") { _, _ -> }
//
//          val dialog = builder.create()
//          dialog.show()
//        }
//
//        buttonLogin.unlockButton()
//      }
//      override fun onFailure(call: Call<UserLoginSuccess?>?, failureResponse: Throwable) {
//        val builder = AlertDialog.Builder(this@UserLoginActivity)
//
//        builder.setTitle(getString(R.string.login_failure))
//        builder.setMessage(failureResponse.message.toString())
//        builder.setNeutralButton("OK") { _, _ -> }
//
//        val dialog = builder.create()
//        dialog.show()
//
//        buttonLogin.unlockButton()
//      }
//    })
//  }
}