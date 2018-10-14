package com.test.taqtile.takitiletest.models

import com.google.gson.annotations.SerializedName


data class LoginCredentials (
  val email: String,
  val password: String,
  val rememberMe: Boolean
)

data class LoginResponse(
  @SerializedName("data") val data: Data
) {

  data class Data(
    @SerializedName("user") val user: LoginUser,
    @SerializedName("token") val token: String
  )

  data class LoginUser(
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

data class ListUserResponse(
  @SerializedName("data") val data: ArrayList<User>,
  @SerializedName("pagination") val pagination: Pagination
) {

  data class Pagination(
    @SerializedName("page") val page: Int,
    @SerializedName("window") val window: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("totalPages") val totalPages: Int
  )
}

data class UserResponse(
    @SerializedName("data") val data: User
)

data class UserCreate(
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("role") val role: String?
)

data class User(
  @SerializedName("id") val id: Int,
  @SerializedName("active") val active: Boolean,
  @SerializedName("email") val email: String,
  @SerializedName("createdAt") val createdAt: String,
  @SerializedName("updatedAt") val updatedAt: String,
  @SerializedName("name") val name: String,
  @SerializedName("role") val role: String
)