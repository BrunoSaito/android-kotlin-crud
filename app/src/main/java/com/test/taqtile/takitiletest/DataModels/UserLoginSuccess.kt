package com.test.taqtile.takitiletest.DataModels

import com.google.gson.annotations.SerializedName


data class UserLoginSuccess(
  @SerializedName("data") val data: Data
) {

  data class Data(
          @SerializedName("user") val user: User,
          @SerializedName("token") val token: String
  ) {

    data class User(
        @SerializedName("id") val id: Int,
        @SerializedName("active") val active: Boolean,
        @SerializedName("email") val email: String,
        @SerializedName("activationToken") val activationToken: Any,
        @SerializedName("createdAt") val createdAt: String,
        @SerializedName("updatedAt") val updatedAt: String,
        @SerializedName("salt") val salt: String,
        @SerializedName("name") val name: String,
        @SerializedName("role") val role: String
    )
  }
}