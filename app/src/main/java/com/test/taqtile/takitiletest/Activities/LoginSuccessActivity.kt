package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.taqtile.takitiletest.Preferences
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_login_success.*

class LoginSuccessActivity : AppCompatActivity() {

  private var name: String? = null
  private var token: String? = null

  private var preferences: HashMap<String?, String?>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_success)

    preferences = Preferences(this@LoginSuccessActivity).getPreferences()
    name = preferences?.get("name")
    token = preferences?.get("token")

    textLoginName?.text = name
    textLoginToken?.text = token

    buttonListUsers.setOnClickListener {
      val nextActivity = Intent(this, UserListActivity::class.java)
      startActivity(nextActivity)
    }
  }
}
