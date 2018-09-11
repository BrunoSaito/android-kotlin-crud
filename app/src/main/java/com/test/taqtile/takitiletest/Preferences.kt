package com.test.taqtile.takitiletest

import android.content.Context
import android.content.SharedPreferences


class Preferences {

  private val LOGIN_NAME = "LOGIN_NAME"
  private val LOGIN_TOKEN = "LOGIN_TOKEN"
  private val PREFS_FILENAME = "com.test.taqtile.takitiletest.prefs"

  private var context: Context? = null
  private var editor: SharedPreferences.Editor? = null

  constructor(context: Context) {
    this.context = context
    val sharedPrefs = context.getSharedPreferences(PREFS_FILENAME, 0)
    this.editor = sharedPrefs.edit()
  }

  companion object Factory {
    var token: String? = null
    var name: String? = null
  }

  fun savePreferences(userName: String?, token: String?) {
    editor?.putString(LOGIN_NAME, userName)
    editor?.putString(LOGIN_TOKEN, token)
    editor?.apply()

    name = userName
    Factory.token = token
  }

  fun saveNewToken(newToken: String?) {
    editor?.putString(LOGIN_TOKEN, newToken)
    editor?.apply()
  }
}