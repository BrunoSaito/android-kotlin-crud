package com.test.taqtile.takitiletest.domain.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.taqtile.takitiletest.*
import com.test.taqtile.takitiletest.mobileUI.userDetails.UserDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private var email: String? = null
  private var password: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

//    buttonLogin.setOnClickListener { login() }
    buttonLogin.setOnClickListener {
      val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)

      startActivity(intent)
    }
  }

  private fun login() {
    email = editTextLoginEmail.getInputText()
    password = editTextLoginPassword.getInputText()

    val validEmail = editTextLoginEmail.validate()
    val validPassword = editTextLoginPassword.validate()

    if (validEmail && validPassword) {
      buttonLogin.lockButton()

//      makeLoginRequest(email, password)
    }
  }

//  private fun makeLoginRequest(email: String?, password: String?) {
//    val userLoginRequest = RetrofitInitializer(Preferences.token).userServices().loginUser(UserLoginCredentials(email, password, false))
//
//    userLoginRequest.enqueue(object: Callback<UserLoginSuccess?> {
//      override fun onResponse(call: Call<UserLoginSuccess?>?, response: Response<UserLoginSuccess?>?) {
//        try {
//          val userName = response!!.body()!!.data!!.user!!.name
//          val token = response.body()!!.data!!.token
//
//          Preferences(this@MainActivity).savePreferences(userName, token)
//
//          val nextActivity = Intent(this@MainActivity, LoginSuccessActivity::class.java)
//
//          startActivity(nextActivity)
//          finish()
//        }
//        catch (e: Exception) {
//          val jsonError = response!!.errorBody()!!.string()
//          val userLoginError = Gson().fromJson(jsonError, UserLoginError::class.java)
//
//          val builder = AlertDialog.Builder(this@MainActivity)
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
//        val builder = AlertDialog.Builder(this@MainActivity)
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
