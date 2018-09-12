package com.test.taqtile.takitiletest

import android.content.Context
import android.content.SharedPreferences


class Utils {

  private val LOGIN_NAME = "LOGIN_NAME"
  private val LOGIN_TOKEN = "LOGIN_TOKEN"
  private val PREFS_FILENAME = "com.test.taqtile.takitiletest.prefs"

  private var context: Context? = null
  private var editor: SharedPreferences.Editor? = null

  private var name: String? = null
  private var token: String? = null
  private var sharedPrefs: SharedPreferences? = null

  constructor(context: Context) {
    this.context = context
    this.sharedPrefs = context.getSharedPreferences(PREFS_FILENAME, 0)
    this.editor = this.sharedPrefs!!.edit()
  }

  fun getPreferences(): HashMap<String, String> {
    val preferences: HashMap<String, String>? = HashMap()

    name = sharedPrefs!!.getString(LOGIN_NAME, "")
    token = sharedPrefs!!.getString(LOGIN_TOKEN, "")

    preferences!!["name"] =  name!!
    preferences["token"] =  token!!

    return preferences
  }

  fun savePreferences(userName: String, token: String) {
    editor!!.putString(LOGIN_NAME, userName)
    editor!!.putString(LOGIN_TOKEN, token)
    editor!!.apply()
  }

  fun saveNewToken(newToken: String?) {
    editor!!.putString(LOGIN_TOKEN, newToken)
    editor!!.apply()
  }
}