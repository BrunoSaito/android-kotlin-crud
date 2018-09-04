package com.test.taqtile.takitiletest

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.test.taqtile.takitiletest.R.string.email
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var textEmail: EditText? = null
    private var textPwd: EditText? = null
    private var buttonLogin: Button? = null
    private var progressBar: ProgressBar? = null

    private val minPwdLength: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textEmail = findViewById<EditText>(R.id.text_email)
        textPwd = findViewById<EditText>(R.id.text_password)
        buttonLogin = findViewById<Button>(R.id.button_login)
        progressBar = findViewById<ProgressBar>(R.id.progress_login)

        buttonLogin!!.setOnClickListener { login() }
        progressBar!!.visibility = View.INVISIBLE
    }

    fun login() {
        if (validate()) {
            progressBar!!.visibility = ProgressBar.VISIBLE
            buttonLogin!!.text = ""
            buttonLogin!!.isEnabled = false

            // Send request to server

            progressBar!!.visibility = ProgressBar.GONE
            buttonLogin!!.text = getString(R.string.login)

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
