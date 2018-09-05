package com.test.taqtile.takitiletest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

class LoginSuccess : AppCompatActivity() {

    private var textLoginName: TextView? = null
    private var textLoginToken: TextView? = null

    private val LOGIN_NAME = "LOGIN_NAME"
    private val LOGIN_TOKEN = "LOGIN_TOKEN"
    private val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"

    private var name: String? = null
    private var token: String? = null
    private var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)

        textLoginName = findViewById<TextView>(R.id.text_login_name)
        textLoginToken = findViewById<TextView>(R.id.text_login_token)

//        getBundle()

        sharedPrefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        name = sharedPrefs!!.getString(LOGIN_NAME, "")
        token = sharedPrefs!!.getString(LOGIN_TOKEN, "")

        textLoginName!!.text = name
        textLoginToken!!.text = token
    }

    private fun getBundle() {
        name = intent.getStringExtra(LOGIN_NAME)
        token = intent.getStringExtra(LOGIN_TOKEN)

        Log.d("D", "name: ${name}")
        Log.d("D", "token: ${token}")
    }
}
