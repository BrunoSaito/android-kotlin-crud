package com.test.taqtile.takitiletest.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.activity_new_user_form.*

class NewUserFormActivity : AppCompatActivity() {

  private val spinnerItems = arrayOf("Usuário", "Administrador")

  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var role: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_user_form)

    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerNewUserRole.adapter = spinnerAdapter

    spinnerNewUserRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        role = spinnerItems.get(position)
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    }

    buttonNewUserSubmit.setOnClickListener {
      name = newUserName.text.toString()
      email = newUserEmail.text.toString()
      password = newUserPassword.text.toString()
    }
  }
}
