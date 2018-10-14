package com.test.taqtile.takitiletest.presentation.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.core.config.Constants
import com.test.taqtile.takitiletest.domain.DetailsUserUseCase
import com.test.taqtile.takitiletest.models.User
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_details.*
import javax.inject.Inject

class UserDetailsActivity : AppCompatActivity() {

  @Inject
  lateinit var getUserDetailsUserUseCase: DetailsUserUseCase

  private var disposables: MutableList<Disposable> = mutableListOf()
  private var userId: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_details)
    AndroidInjection.inject(this)

    userId = intent.getStringExtra(Constants.USER_ID)

    setupActionBar()
    val localUserId = userId
    if (localUserId != null) getDetails(localUserId)
    setupEditButton()
    setupDeleteButton()

    if (!intent.getStringExtra("message").isNullOrEmpty()) {
      val builder = AlertDialog.Builder(this@UserDetailsActivity)
      builder.setTitle("Mensagem")
      builder.setMessage(intent.getStringExtra("message"))
      builder.setNeutralButton("OK") { _, _ -> }

      val dialog = builder.create()
      dialog.show()
    }

    progressBarDetailsUser.visibility = ProgressBar.VISIBLE
  }

  override fun onBackPressed() {
    val intent = Intent(this@UserDetailsActivity, UserListActivity::class.java)

    startActivity(intent)
    finish()
  }
  // end region

  // region setup
  private fun setupActionBar() {
    val actionBar = supportActionBar
    actionBar?.title = getString(R.string.user_details_title)
  }

  private fun setupEditButton() {
    user_details_button_edit.setOnClickListener {
      val intent = Intent(this@UserDetailsActivity, UserFormActivity::class.java)

      intent.putExtra(Constants.USER_ID, userId)

      startActivity(intent)
      finish()
    }
  }

  private fun setupDeleteButton() {
    user_details_button_delete.setOnClickListener {
      val builder = AlertDialog.Builder(this@UserDetailsActivity)
      builder.setMessage("Deseja realmente deletar o usuário " + user_details_name.text + "?")
      builder.setPositiveButton("Sim") { _, _ ->
        //        deleteUser(userId)
      }
      builder.setNegativeButton("Não") { dialog, _ ->
        dialog.dismiss()
      }

      val dialog = builder.create()
      dialog.show()
    }
  }
  // end region

  // region services
  private fun getDetails(id: String) {
    disposables.add(
            getUserDetailsUserUseCase.execute(id)
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
    user_details_name.text = user.name
    user_details_email.text = user.email
    user_details_role.text = user.role

    progressBarDetailsUser.visibility = View.GONE
  }

  private fun onFailure(message: String?) {
    //TODO add snack bar with error message
  }
  // end region

//  private fun deleteUser(userId: String?) {
//    val userDelete = RetrofitInitializer(Preferences.token).userServices().deleteUser(userId)
//
//    userDelete.enqueue(object: Callback<DeleteUserSuccess?> {
//      override fun onResponse(call: Call<DeleteUserSuccess?>?, response: Response<DeleteUserSuccess?>?) {
//        try {
//          if (response!!.body()!!.data!!.active == false) {
//            val intent = Intent(this@UserDetailsActivity, UserListActivity::class.java)
//
//            intent.putExtra("message", "Usuário deletado com sucesso!")
//            startActivity(intent)
//            finish()
//          }
//        }
//        catch (e: Exception) {
//
//        }
//      }
//
//      override fun onFailure(call: Call<DeleteUserSuccess?>?, failureResponse: Throwable) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//      }
//    })
//  }
}
