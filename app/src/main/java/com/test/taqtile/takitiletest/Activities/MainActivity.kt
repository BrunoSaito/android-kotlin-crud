package com.test.taqtile.takitiletest.Activities

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.test.taqtile.takitiletest.*
import com.test.taqtile.takitiletest.DataModels.UserLoginCredentials
import com.test.taqtile.takitiletest.DataModels.UserLoginError
import com.test.taqtile.takitiletest.DataModels.UserLoginResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

  private val minPwdLength = 4
  private val LOGIN_NAME = "LOGIN_NAME"
  private val LOGIN_TOKEN = "LOGIN_TOKEN"
  private val PREFS_FILENAME = "com.test.taqtile.takitiletest.prefs"

  private var name: String? = null
  private var token: String? = null
  private var sharedPrefs: SharedPreferences? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonLogin!!.setOnClickListener { login() }
    progressBarLogin!!.visibility = View.GONE

    getPreferences()
  }

  private fun getPreferences() {
    sharedPrefs = this.getSharedPreferences(PREFS_FILENAME, 0)
    name = sharedPrefs!!.getString(LOGIN_NAME, "")
    token = sharedPrefs!!.getString(LOGIN_TOKEN, "")
  }

  private fun login() {
    val email = textEmail!!.text.toString()
    val password = textPassword!!.text.toString()

    if (validate()) {
      lockLoginButton()

      makeRequest(email, password)
    }
  }

  private fun makeRequest(email: String, password: String) {
    val userLogin = RetrofitInitializer().userLoginService().loginUser(UserLoginCredentials(email, password, false))

    userLogin.enqueue(object: Callback<UserLoginResponse?> {
      override fun onResponse(call: Call<UserLoginResponse?>?, response: Response<UserLoginResponse?>?) {
        try {
          val editor = sharedPrefs!!.edit()
          editor.putString(LOGIN_NAME, response!!.body()!!.data.user.name)
          editor.putString(LOGIN_TOKEN, response.body()!!.data.token)
          editor.apply()

          val nextActivity = Intent(this@MainActivity, LoginSuccessActivity::class.java)
          startActivity(nextActivity)
        }
        catch (e: Exception) {
          val jsonError = response!!.errorBody()!!.string()
          val userLoginError = Gson().fromJson(jsonError, UserLoginError::class.java)

          val builder = AlertDialog.Builder(this@MainActivity)

          builder.setTitle("Erro no login.")
          builder.setMessage(userLoginError.errors.get(0).message)
          builder.setNeutralButton("OK") { _, _ -> }

          val dialog = builder.create()
          dialog.show()
        }

        unlockLoginButton()
      }
      override fun onFailure(call: Call<UserLoginResponse?>?, t: Throwable?) {
        unlockLoginButton()
      }
    }
    )
  }

  private fun validate(): Boolean {
    val email = textEmail!!.text.toString()
    val pwd = textPassword!!.text.toString()
    var validEmail = false
    var validPwd = false

    if (validateEmail(email)) {
      textErrorEmail!!.visibility = TextView.GONE
      validEmail = true
    }
    if (validatePwd(pwd)) {
      textErrorPassword!!.visibility = TextView.GONE
      validPwd = true
    }

    return (validEmail && validPwd)
  }

  private fun validateEmail(email: String) : Boolean {
    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      textEmail!!.background.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorEmail!!.text = getString(R.string.email_invalid)
      textErrorEmail!!.visibility = TextView.VISIBLE

      return false
    }
    return true
  }

  private fun validatePwd(pwd: String) : Boolean {
    if (pwd.length < minPwdLength) {
      textPassword!!.background.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorPassword!!.text = getString(R.string.pwd_invalid)
      textErrorPassword!!.visibility = TextView.VISIBLE

      return false
    }
    return true
  }

  private fun lockLoginButton() {
    progressBarLogin!!.visibility = ProgressBar.VISIBLE
    buttonLogin!!.text = ""
    buttonLogin!!.isEnabled = false
  }

  private fun unlockLoginButton() {
    progressBarLogin!!.visibility = ProgressBar.GONE
    buttonLogin!!.text = getString(R.string.login)
    buttonLogin!!.isEnabled = true
  }
}
