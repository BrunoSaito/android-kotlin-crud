package com.test.taqtile.takitiletest.models

import com.google.gson.annotations.SerializedName


data class CreateNewUserSuccess(
  @SerializedName("data") val data: Data?
) {

  data class Data(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("active") val active: Boolean?,
    @SerializedName("id") val id: Int?,
    @SerializedName("updatedAt") val updatedAt: String?,
    @SerializedName("createdAt") val createdAt: String?
  )
}