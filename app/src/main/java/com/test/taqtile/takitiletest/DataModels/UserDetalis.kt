package com.test.taqtile.takitiletest.DataModels

import com.google.gson.annotations.SerializedName


data class UserDetails(
  @SerializedName("data") val data: Data?
) {

  data class Data(
    @SerializedName("id") val id: Int?,
    @SerializedName("active") val active: Boolean?,
    @SerializedName("email") val email: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("role") val role: String?
  )
}