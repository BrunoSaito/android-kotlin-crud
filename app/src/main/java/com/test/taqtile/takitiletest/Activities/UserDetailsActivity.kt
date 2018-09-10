package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.test.taqtile.takitiletest.DataModels.UserDetails
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.RetrofitInitializer
import com.test.taqtile.takitiletest.Utils
import kotlinx.android.synthetic.main.activity_user_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserDetailsActivity : AppCompatActivity() {

  private var token: String? = null
  private var userId: String? = null

  private var preferences: HashMap<String, String>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_details)

    preferences = Utils(this@UserDetailsActivity).getPreferences()
    token = preferences!!.get("token")

    userId = intent.getStringExtra("id")
    getDetailsRequest(token, userId)

    buttonUserDetailsEdit.setOnClickListener {
      val intent = Intent(this@UserDetailsActivity, EditUserActivity::class.java)

      intent.putExtra("userName", userDetailName.text)
      intent.putExtra("userEmail", userDetailEmail.text)
      intent.putExtra("userRole", userDetailRole.text)
      startActivity(intent)
    }

    progressBarDetailsUser.visibility = ProgressBar.VISIBLE
  }

  fun getDetailsRequest(token: String?, userId: String?) {
    val userDetails = RetrofitInitializer(token).userServices().getUserDetails(userId)

    userDetails.enqueue(object : Callback<UserDetails?> {
      override fun onResponse(call: Call<UserDetails?>, response: Response<UserDetails?>) {
        userDetailName.text = response.body()!!.data.name
        userDetailEmail.text = response.body()!!.data.email
        userDetailRole.text = response.body()!!.data.role

        progressBarDetailsUser.visibility = ProgressBar.GONE
      }

      override fun onFailure(call: Call<UserDetails?>, t: Throwable) {
        progressBarDetailsUser.visibility = ProgressBar.GONE
      }
    })
  }
}
