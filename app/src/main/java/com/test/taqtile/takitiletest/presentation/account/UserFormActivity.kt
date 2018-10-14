package com.test.taqtile.takitiletest.presentation.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.test.taqtile.takitiletest.*
import com.test.taqtile.takitiletest.core.config.Constants
import com.test.taqtile.takitiletest.domain.DetailsUserUseCase
import com.test.taqtile.takitiletest.models.User
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_form.*
import javax.inject.Inject

class UserFormActivity : AppCompatActivity() {

  @Inject
  lateinit var getUserDetails: DetailsUserUseCase

  private var disposables: MutableList<Disposable> = mutableListOf()
  private val spinnerItems = HashMap<String, String>()

  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var passwordConfirm: String? = null
  private var role: String? = null
  private var id: String? = null

  // region lifecycle
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_form)
    AndroidInjection.inject(this)

    setupActionBar()
    setupForm()
    setupButton()
  }

  override fun onBackPressed() {
    val intent = Intent(this@UserFormActivity, UserListActivity::class.java)

    startActivity(intent)
    finish()
  }
  // end region

  // region setup
  private fun setupActionBar() {
    val actionBar = supportActionBar
    actionBar?.title = getString(R.string.new_user_title)
  }

  private fun setupForm() {
    setupSpinner()
    setupFields()
  }

  private fun setupSpinner() {
    spinnerItems["Usuário"] = "user"
    spinnerItems["Administrador"] = "admin"

    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toTypedArray())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    user_form_spinner_role.adapter = spinnerAdapter

    user_form_spinner_role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val keyArray = spinnerItems.keys.toTypedArray()
        role = spinnerItems[keyArray[position]]
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }
  }

  private fun setupButton() {
    user_form_button_submit.setOnClickListener {
      name = user_form_name.getInputText()
      email = user_form_email.getInputText()
      password = user_form_password.getInputText()
      passwordConfirm = user_form_password_confirmation.getInputText()

      val validName = user_form_name.validate()
      val validEmail = user_form_email.validate()
      val validPassword = user_form_password.validate()

      if (validName && validEmail && validPassword) {
        if (password.equals(passwordConfirm)) {
          user_form_password_confirmation.hideErrorText()

          user_form_button_submit.lockButton()

//          submitNewUserRequest(name, password, email, role)
        }
        else {
          user_form_password_confirmation.showErrorText()
        }
      }
    }
  }

  private fun setupFields() {
    id = intent.getStringExtra(Constants.USER_ID)

    val localId = id
    Log.d("D", "local id : $localId")
    if (localId != null) {
      user_form_password.visibility = View.GONE
      user_form_password_confirmation.visibility = View.GONE

      getDetails(localId)
    }
  }
  // end region

  // region services
  private fun getDetails(id: String) {
    progress_bar_user_form.visibility = ProgressBar.VISIBLE
    disposables.add(
            getUserDetails.execute(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                      onSuccess(it.data)
                    }, {
                      onFailure(it.message)
                    })
    )
  }
  // end region

  // region private
  private fun onSuccess(user: User) {
    setUserInfo(user)
    progress_bar_user_form.visibility = ProgressBar.GONE
  }

  private fun onFailure(message: String?) {
    Log.d("D", "failed : ${message}")
    progress_bar_user_form.visibility = ProgressBar.GONE
  }

  private fun setUserInfo(user: User) {
    Log.d("D", "user : $user")
    //TODO make a component of fields of the form and then create a method setUserInfo on the created component
    user_form_name.setInputText(user.name)
    user_form_email.setInputText(user.email)
    user_form_spinner_role.setSelection(spinnerItems.values.indexOf(user.role))
  }
  // end region

//  private fun submitNewUserRequest(name: String?, password: String?, email: String?, role: String?) {
//    val newUserRequest = RetrofitInitializer(Preferences.token).userServices().createNewUser(CreateNewUserData(name, password, email, role))
//
//    newUserRequest.enqueue(object : Callback<CreateNewUserSuccess?> {
//      override fun onResponse(call: Call<CreateNewUserSuccess?>?, response: Response<CreateNewUserSuccess?>?) {
//        try {
//          response!!.body()!!.data!!.name!!
//
//          val intent = Intent(this@CreateNewUserActivity, UserListActivity::class.java)
//          intent.putExtra("message", getString(R.string.new_user_success))
//
//          startActivity(intent)
//          finish()
//        }
//        catch (e: Exception) {
//          val jsonError = response?.errorBody()?.string()
//          val createNewUserError = Gson().fromJson(jsonError, CreateNewUserError::class.java)
//
//          val builder = AlertDialog.Builder(this@CreateNewUserActivity)
//
//          builder.setTitle(getString(R.string.new_user_failure))
//          builder.setMessage(createNewUserError.errors?.get(0)?.original)
//          builder.setNeutralButton("OK") { _, _ -> }
//
//          val dialog = builder.create()
//          dialog.show()
//        }
//
//        buttonSubmitNewUser.unlockButton()
//      }
//      override fun onFailure(call: Call<CreateNewUserSuccess?>?, failureResponse: Throwable) {
//        val builder = AlertDialog.Builder(this@CreateNewUserActivity)
//
//        builder.setTitle(getString(R.string.new_user_failure))
//        builder.setMessage(failureResponse.message.toString())
//        builder.setNeutralButton("OK") { _, _ -> }
//
//        val dialog = builder.create()
//        dialog.show()
//
//        buttonSubmitNewUser.unlockButton()
//      }
//    })
//  }
}
