package com.test.taqtile.takitiletest.Activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.test.taqtile.takitiletest.*
import com.test.taqtile.takitiletest.DataModels.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

  private val minPasswordLength = 4

  private var name: String? = null
  private var token: String? = null
  private var email: String? = null
  private var password: String? = null

  private var preferences: HashMap<String?, String?>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonLogin?.setOnClickListener { login() }

    progressBarLogin?.visibility = View.GONE

    preferences = Preferences(this@MainActivity).getPreferences()
    name = preferences?.get("name")
    token = preferences?.get("token")

    textPassword?.background?.mutate()?.setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    textEmail?.background?.mutate()?.setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
  }

  private fun login() {
    email = textEmail?.text.toString()
    password = textPassword?.text.toString()

    if (validate(email, password)) {
      lockLoginButton()

      loginRequest(email, password)
    }
  }

  private fun loginRequest(email: String?, password: String?) {
    val userLoginRequest = RetrofitInitializer("").userServices().loginUser(UserLoginCredentials(email, password, false))

    userLoginRequest.enqueue(object: Callback<UserLoginSuccess?> {
      override fun onResponse(call: Call<UserLoginSuccess?>?, response: Response<UserLoginSuccess?>?) {
        try {
          val userName = response!!.body()!!.data!!.user!!.name
          val token = response.body()!!.data!!.token

          Preferences(this@MainActivity).savePreferences(userName, token)

          val nextActivity = Intent(this@MainActivity, LoginSuccessActivity::class.java)
          startActivity(nextActivity)
        }
        catch (e: Exception) {
          val jsonError = response!!.errorBody()!!.string()
          val userLoginError = Gson().fromJson(jsonError, UserLoginError::class.java)

          val builder = AlertDialog.Builder(this@MainActivity)

          builder.setTitle(getString(R.string.login_failure))
          builder.setMessage(userLoginError.errors!!.get(0).message)
          builder.setNeutralButton("OK") { _, _ -> }

          val dialog = builder.create()
          dialog.show()
        }

        unlockLoginButton()
      }
      override fun onFailure(call: Call<UserLoginSuccess?>?, failureResponse: Throwable) {
        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle(getString(R.string.login_failure))
        builder.setMessage(failureResponse.message.toString())
        builder.setNeutralButton("OK") { _, _ -> }

        val dialog = builder.create()
        dialog.show()

        unlockLoginButton()
      }
    }
    )
  }

  private fun validate(email: String?, password: String?): Boolean {
    return (validateEmail(email) && validatePassword(password))
  }

  private fun validateEmail(email: String?) : Boolean {
    if (email?.isEmpty()!! || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      textEmail?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorEmail?.text = getString(R.string.invalid_email)
      textErrorEmail?.visibility = TextView.VISIBLE

      return false
    }
    textEmail?.background?.mutate()?.setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    textErrorEmail?.visibility = TextView.GONE

    return true
  }

  private fun validatePassword(password: String?) : Boolean {
    if (password?.length!! < minPasswordLength) {
      textPassword?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorPassword?.text = getString(R.string.invalid_password)
      textErrorPassword?.visibility = TextView.VISIBLE

      return false
    }
    textPassword?.background?.mutate()?.setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    textErrorPassword?.visibility = TextView.GONE

    return true
  }

  private fun lockLoginButton() {
    progressBarLogin?.visibility = ProgressBar.VISIBLE
    buttonLogin?.text = ""
    buttonLogin.isEnabled = false
  }

  private fun unlockLoginButton() {
    progressBarLogin?.visibility = ProgressBar.GONE
    buttonLogin?.text = getString(R.string.login)
    buttonLogin.isEnabled = true
  }
}
