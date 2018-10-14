package com.test.taqtile.takitiletest.presentation.account

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.core.config.Constants
import com.test.taqtile.takitiletest.domain.DeleteUserUseCase
import com.test.taqtile.takitiletest.domain.DetailsUserUseCase
import com.test.taqtile.takitiletest.models.User
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_details.*
import widgets.components.CustomSnackBarBuilder
import javax.inject.Inject

class UserDetailsActivity : AppCompatActivity() {

  @Inject
  lateinit var getUserDetailsUserUseCase: DetailsUserUseCase

  @Inject
  lateinit var deleteUserUseCase: DeleteUserUseCase

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
      builder.setMessage(getString(R.string.delete_user_alert_message, user_details_name.text))
      builder.setPositiveButton(R.string.delete_user_alert_positive_button) { _, _ ->
        val localId = userId
        if (localId != null) delete(localId)
      }
      builder.setNegativeButton(R.string.delete_user_alert_negative_button) { dialog, _ ->
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
                      onGetDetailsSuccess(it.data)
                    }, {
                      onFailure(it.message)
                    })
    )
  }

  private fun delete(id: String) {
    progressBarDetailsUser.visibility = ProgressBar.VISIBLE
    disposables.add(
            deleteUserUseCase.execute(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                      onDeleteSuccess(it.data)
                    }, {
                      onFailure(it.message)
                    })
    )
  }
  // end region

  // region private
  private fun onGetDetailsSuccess(user: User) {
    user_details_name.text = user.name
    user_details_email.text = user.email
    user_details_role.text = user.role

    progressBarDetailsUser.visibility = View.GONE
  }

  private fun onDeleteSuccess(user: User) {
    if (!user.active) {
      setResult(Activity.RESULT_OK, intent)
      finish()
    } else {
      CustomSnackBarBuilder(this@UserDetailsActivity)
              .withText(R.string.create_user_failure)
              .show()
      progressBarDetailsUser.visibility = View.GONE
    }
  }

  private fun onFailure(message: String?) {
    CustomSnackBarBuilder(this@UserDetailsActivity)
            .withText(message)
            .show()
    progressBarDetailsUser.visibility = View.GONE
  }
  // end region
}
