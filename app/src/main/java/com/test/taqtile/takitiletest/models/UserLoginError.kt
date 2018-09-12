package com.test.taqtile.takitiletest.models

import com.google.gson.annotations.SerializedName


data class UserLoginError(
  @SerializedName("data") val data: Any?,
  @SerializedName("errors") val errors: ArrayList<Error>?
) {

  data class Error(
    @SerializedName("name") val name: String?,
    @SerializedName("original") val original: String?,
    @SerializedName("message") val message: String?
  )
}