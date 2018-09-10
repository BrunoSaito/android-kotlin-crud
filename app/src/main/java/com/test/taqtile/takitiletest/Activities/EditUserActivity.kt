package com.test.taqtile.takitiletest.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_edit_user.*


class EditUserActivity : AppCompatActivity() {

  var userName: String? = null
  var userEmail: String? = null
  var userRole: String? = null

  private val spinnerItems = HashMap<String, String>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_user)

    spinnerItems.put("Usuário", "user")
    spinnerItems.put("Administrador", "admin")

    userName = intent.getStringExtra("userName")
    userEmail = intent.getStringExtra("userEmail")
    userRole = intent.getStringExtra("userRole")

    textEditUserName.setText(userName, TextView.BufferType.EDITABLE)
    textEditUserEmail.setText(userEmail, TextView.BufferType.EDITABLE)

    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toTypedArray())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerEditUserRole.adapter = spinnerAdapter

    var i = 0
    for ((key, value) in spinnerItems) {
      if (value == userRole)
        spinnerEditUserRole.setSelection(i)
      i++
    }

    progressBarEditUser.visibility = ProgressBar.GONE
  }
}
