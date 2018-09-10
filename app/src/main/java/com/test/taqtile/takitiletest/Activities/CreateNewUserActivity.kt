package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.test.taqtile.takitiletest.DataModels.CreateNewUserData
import com.test.taqtile.takitiletest.DataModels.CreateNewUserError
import com.test.taqtile.takitiletest.DataModels.CreateNewUserSuccess
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.RetrofitInitializer
import com.test.taqtile.takitiletest.Utils
import kotlinx.android.synthetic.main.activity_new_user_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateNewUserActivity : AppCompatActivity() {

  private val minPasswordLength = 4

  private val spinnerItems = HashMap<String, String>()

  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var role: String? = null
  private var token: String? = null

  private var preferences: HashMap<String, String>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_user_form)

    spinnerItems.put("Usuário", "user")
    spinnerItems.put("Administrador", "admin")

    preferences = Utils(this@CreateNewUserActivity).getPreferences()
    token = preferences!!.get("token")

    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toTypedArray())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerNewUserRole.adapter = spinnerAdapter

    spinnerNewUserRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var i = 0
        for ((key, value) in spinnerItems) {
          if (i == position)
            role = spinnerItems.get(key)
          i++
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }

    buttonNewUserSubmit.setOnClickListener {
      name = textNewUserName.text.toString()
      email = textNewUserEmail.text.toString()
      password = textNewUserPassword.text.toString()

      if (validate(name!!, email!!, password!!)) {
        lockSubmitButton()

        submitNewUserRequest(name!!, password!!, email!!, role!!)
      }
    }

    progressBarNewUser!!.visibility = ProgressBar.GONE

    textNewUserName!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    textNewUserEmail!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    textNewUserPassword!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
  }

  private fun submitNewUserRequest(name: String, password: String, email: String, role: String) {
    val newUserRequest = RetrofitInitializer(token).userServices().createNewUser(CreateNewUserData(name, password, email, role))

    newUserRequest.enqueue(object : Callback<CreateNewUserSuccess?> {
      override fun onResponse(call: Call<CreateNewUserSuccess?>?, response: Response<CreateNewUserSuccess?>?) {
        try {
          response!!.body()!!.data.user.name

          val intent = Intent(this@CreateNewUserActivity, UserListActivity::class.java)
          intent.putExtra("message", getString(R.string.new_user_success))
          startActivity(intent)
        }
        catch (e: Exception) {
          val jsonError = response!!.errorBody()!!.string()
          val createNewUserError = Gson().fromJson(jsonError, CreateNewUserError::class.java)

          val builder = AlertDialog.Builder(this@CreateNewUserActivity)

          builder.setTitle(getString(R.string.new_user_failure))
          builder.setMessage(createNewUserError.errors.get(0).original)
          builder.setNeutralButton("OK") { _, _ -> }

          val dialog = builder.create()
          dialog.show()
        }

        unlockSubmitButton()
      }
      override fun onFailure(call: Call<CreateNewUserSuccess?>?, t: Throwable) {
        val builder = android.app.AlertDialog.Builder(this@CreateNewUserActivity)

        builder.setTitle(getString(R.string.new_user_failure))
        builder.setMessage(t.message.toString())
        builder.setNeutralButton("OK") { _, _ -> }

        val dialog = builder.create()
        dialog.show()

        unlockSubmitButton()
      }
    })
  }

  private fun validate(name: String, email: String, password: String): Boolean {
    var validName = false
    var validEmail = false
    var validPassword = false

    if (validateName(name)) {
      textErrorNewUserName!!.visibility = TextView.GONE
      validName = true
    }
    if (validateEmail(email)) {
      textErrorNewUserEmail!!.visibility = TextView.GONE
      validEmail = true
    }
    if (validatePassword(password)) {
      textErrorNewUserPassword!!.visibility = TextView.GONE
      validPassword = true
    }

    return (validName && validEmail && validPassword)
  }

  private fun validateName(name: String): Boolean {
    if (name.isEmpty() || !name.matches("\\D+".toRegex())) {
      textNewUserName!!.background.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorNewUserName!!.text = getString(R.string.invalid_name)
      textErrorNewUserName!!.visibility = TextView.VISIBLE
    }
    textNewUserName!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    return true
  }

  private fun validateEmail(email: String): Boolean {
    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      textNewUserEmail!!.background.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorNewUserEmail!!.text = getString(R.string.invalid_email)
      textErrorNewUserEmail!!.visibility = TextView.VISIBLE

      return false
    }
    textNewUserEmail!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    return true
  }

  private fun validatePassword(password: String): Boolean {
    if (password.length < minPasswordLength) {
      textNewUserPassword!!.background.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorNewUserPassword!!.text = getString(R.string.invalid_password)
      textErrorNewUserPassword!!.visibility = TextView.VISIBLE

      return false
    }
    textNewUserPassword!!.background.mutate().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
    return true
  }

  private fun lockSubmitButton() {
    progressBarNewUser!!.visibility = ProgressBar.VISIBLE
    buttonNewUserSubmit!!.text = ""
    buttonNewUserSubmit.isEnabled = false
  }

  private fun unlockSubmitButton() {
    progressBarNewUser!!.visibility = ProgressBar.GONE
    buttonNewUserSubmit!!.text = getString(R.string.new_user_submit)
    buttonNewUserSubmit.isEnabled = true
  }
}
