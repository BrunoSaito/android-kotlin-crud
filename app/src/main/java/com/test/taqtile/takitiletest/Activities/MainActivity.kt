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
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

  private val minPwdLength = 4
  private val url = "https://tq-template-server-sample.herokuapp.com/authenticate"
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
    val pwd = textPassword!!.text.toString()

    if (validate()) {
      lockLoginButton()

      // Send request to server
      val queue = Volley.newRequestQueue(this)

      val jsonPost: JSONObject? = JSONObject()
      jsonPost!!.put("password", pwd)
      jsonPost.put("email", email)
      jsonPost.put("rememberMe", false)

      val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonPost,
            Response.Listener { response ->
              val editor = sharedPrefs!!.edit()
              editor.putString(LOGIN_NAME, response.getJSONObject("data").getJSONObject("user").getString("name"))
              editor.putString(LOGIN_TOKEN, response.getJSONObject("data").getString("token"))
              editor.apply()

              unlockLoginButton()

              val nextActivity = Intent(this, UserListActivity::class.java)
              startActivity(nextActivity)
            },
            Response.ErrorListener { error ->
              val networkError: NetworkResponse? = error.networkResponse

              val jsonErrorString = String(
                      networkError!!.data ?: ByteArray(0),
                      Charset.forName(HttpHeaderParser.parseCharset(networkError.headers)))

              val jsonError = JSONObject(jsonErrorString)
              val jsonErrorMessage = JSONObject(jsonError.getJSONArray("errors").getString(0))

              val builder = AlertDialog.Builder(this@MainActivity)

              builder.setTitle("Erro no login.")
              builder.setMessage(jsonErrorMessage.getString("message"))
              builder.setNeutralButton("Ok") { _ ,_ -> }

              val dialog: AlertDialog = builder.create()
              dialog.show()

              unlockLoginButton()
            }
      )

      queue.add(jsonObjectRequest)
    }
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
