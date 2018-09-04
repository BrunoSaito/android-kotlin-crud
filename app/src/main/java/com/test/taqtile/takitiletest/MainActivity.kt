package com.test.taqtile.takitiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.test.taqtile.takitiletest.R.string.email
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var textEmail: EditText? = null
    var textPwd: EditText? = null
    private var buttonLogin: Button? = null

    private val minPwdLength: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textEmail = findViewById(R.id.text_email)
        textPwd = findViewById(R.id.text_password)
        buttonLogin = findViewById(R.id.button_login)

        buttonLogin!!.setOnClickListener { login() }
    }

    fun login() {
        if (validate()) {
            // Go to login success activity.
        }
    }

    fun validate(): Boolean {
        val email = textEmail!!.text.toString()
        val pwd = textEmail!!.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail!!.error = "Insira um e-mail válido."
            return false
        }
        if (pwd.length <= minPwdLength) {
            textPwd!!.error = "A senha deve conter, no mínimo, 4 caracteres."
            return false
        }
        return true
    }
}
