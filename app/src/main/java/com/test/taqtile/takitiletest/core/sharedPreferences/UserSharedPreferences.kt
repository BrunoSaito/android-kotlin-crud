package com.test.taqtile.takitiletest.core.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.test.taqtile.takitiletest.providers.config.Constants


class UserSharedPreferences(val context: Context) {
  private var preferences: SharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE)

  fun saveAccessToken(acessToken: String?) {
    preferences.edit().putString(Constants.LOGIN_TOKEN, acessToken).apply()
  }

  fun getAccessToken(): String? {
    return preferences.getString(Constants.LOGIN_TOKEN, null)
  }

  fun saveUserName(userName: String?) {
    preferences.edit().putString(Constants.LOGIN_NAME, userName).apply()
  }

  fun getUserName(): String {
    return preferences.getString(Constants.LOGIN_NAME, "")
  }
}