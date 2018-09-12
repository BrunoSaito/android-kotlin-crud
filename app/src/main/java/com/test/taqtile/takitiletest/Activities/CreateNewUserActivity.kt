package com.test.taqtile.takitiletest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.test.taqtile.takitiletest.*
import com.test.taqtile.takitiletest.DataModels.CreateNewUserData
import com.test.taqtile.takitiletest.DataModels.CreateNewUserError
import com.test.taqtile.takitiletest.DataModels.CreateNewUserSuccess
import kotlinx.android.synthetic.main.activity_new_user_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateNewUserActivity : AppCompatActivity() {

  private val spinnerItems = HashMap<String, String>()

  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var passwordConfirm: String? = null
  private var role: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_user_form)

    val actionBar = supportActionBar
    actionBar?.title = getString(R.string.new_user_title)

    spinnerItems["Usuário"] = "user"
    spinnerItems["Administrador"] = "admin"

    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toTypedArray())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerNewUserRole.adapter = spinnerAdapter

    spinnerNewUserRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val keyArray = spinnerItems.keys.toTypedArray()
        role = spinnerItems[keyArray[position]]
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }

    buttonSubmitNewUser.setOnClickListener {
      name = editTextNewUserName.getInputText()
      email = editTextNewUserEmail.getInputText()
      password = editTextNewUserPassword.getInputText()
      passwordConfirm = editTextNewUserPasswordConfirm.getInputText()

      val validName = editTextNewUserName.validate()
      val validEmail = editTextNewUserEmail.validate()
      val validPassword = editTextNewUserPassword.validate()

      if (validName && validEmail && validPassword) {
        if (password.equals(passwordConfirm)) {
          editTextNewUserPasswordConfirm.hideErrorText()

          buttonSubmitNewUser.lockButton()

          submitNewUserRequest(name, password, email, role)
        }
        else {
          editTextNewUserPasswordConfirm.showErrorText()
        }
      }
    }
  }

  override fun onBackPressed() {
    val intent = Intent(this@CreateNewUserActivity, UserListActivity::class.java)

    startActivity(intent)
  }

  private fun submitNewUserRequest(name: String?, password: String?, email: String?, role: String?) {
    val newUserRequest = RetrofitInitializer(Preferences.token).userServices().createNewUser(CreateNewUserData(name, password, email, role))

    newUserRequest.enqueue(object : Callback<CreateNewUserSuccess?> {
      override fun onResponse(call: Call<CreateNewUserSuccess?>?, response: Response<CreateNewUserSuccess?>?) {
        try {
          response!!.body()!!.data!!.name!!

          val intent = Intent(this@CreateNewUserActivity, UserListActivity::class.java)
          intent.putExtra("message", getString(R.string.new_user_success))

          startActivity(intent)
          finish()
        }
        catch (e: Exception) {
          val jsonError = response?.errorBody()?.string()
          val createNewUserError = Gson().fromJson(jsonError, CreateNewUserError::class.java)

          val builder = AlertDialog.Builder(this@CreateNewUserActivity)

          builder.setTitle(getString(R.string.new_user_failure))
          builder.setMessage(createNewUserError.errors?.get(0)?.original)
          builder.setNeutralButton("OK") { _, _ -> }

          val dialog = builder.create()
          dialog.show()
        }

        buttonSubmitNewUser.unlockButton()
      }
      override fun onFailure(call: Call<CreateNewUserSuccess?>?, failureResponse: Throwable) {
        val builder = AlertDialog.Builder(this@CreateNewUserActivity)

        builder.setTitle(getString(R.string.new_user_failure))
        builder.setMessage(failureResponse.message.toString())
        builder.setNeutralButton("OK") { _, _ -> }

        val dialog = builder.create()
        dialog.show()

        buttonSubmitNewUser.unlockButton()
      }
    })
  }
}
