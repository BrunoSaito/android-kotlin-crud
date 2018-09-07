package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_login_success.*

class LoginSuccessActivity : AppCompatActivity() {

  private val LOGIN_NAME = "LOGIN_NAME"
  private val LOGIN_TOKEN = "LOGIN_TOKEN"
  private val PREFS_FILENAME = "com.test.taqtile.takitiletest.prefs"

  private var name: String? = null
  private var token: String? = null
  private var sharedPrefs: SharedPreferences? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_success)
    getPreferences()

    textLoginName!!.text = name
    textLoginToken!!.text = token

    Log.d("D", "token: " + token)

    buttonListUsers.setOnClickListener {
      val nextActivity = Intent(this, UserListActivity::class.java)
      startActivity(nextActivity)
    }
  }

  private fun getPreferences() {
    sharedPrefs = this.getSharedPreferences(PREFS_FILENAME, 0)
    name = sharedPrefs!!.getString(LOGIN_NAME, "")
    token = sharedPrefs!!.getString(LOGIN_TOKEN, "")
  }
}
