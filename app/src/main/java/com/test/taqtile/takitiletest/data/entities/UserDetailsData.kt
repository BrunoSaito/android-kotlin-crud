package com.test.taqtile.takitiletest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "UserDetails")
data class UserDetailsData (
  @SerializedName("data") val data: Data? = null
){
  data class Data (
          @PrimaryKey
          @SerializedName("id") val id: Int?,
          @SerializedName("active") val active: Boolean?,
          @SerializedName("email") val email: String?,
          @SerializedName("createdAt") val createdAt: String?,
          @SerializedName("updatedAt") val updatedAt: String?,
          @SerializedName("name") val name: String?,
          @SerializedName("role") val role: String?
  )
}