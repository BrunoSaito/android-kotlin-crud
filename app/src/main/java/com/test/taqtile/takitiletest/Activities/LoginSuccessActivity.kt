package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.taqtile.takitiletest.Preferences
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_login_success.*

class LoginSuccessActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_success)

    textLoginName?.text = Preferences.name
    textLoginToken?.text = Preferences.token

    buttonListUsers.setOnClickListener {
      val nextActivity = Intent(this, UserListActivity::class.java)
      startActivity(nextActivity)
    }
  }
}
