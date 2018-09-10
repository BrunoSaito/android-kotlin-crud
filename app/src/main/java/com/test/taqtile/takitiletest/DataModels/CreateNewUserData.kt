package com.test.taqtile.takitiletest.DataModels

import com.google.gson.annotations.SerializedName


data class CreateNewUserData(
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("role") val role: String?
)