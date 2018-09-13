package com.test.taqtile.takitiletest.data.services

import com.google.gson.annotations.SerializedName
import com.test.taqtile.takitiletest.data.entities.UserData


class UserListResult {
  @SerializedName("results")
  lateinit var users: List<UserData>
}