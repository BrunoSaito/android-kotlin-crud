package com.test.taqtile.takitiletest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "UserDetails")
data class UserDetailsData (
  @SerializedName("data") var data: Data? = null
){
  data class Data (
          @PrimaryKey
          @SerializedName("id") var id: Int?,
          @SerializedName("active") var active: Boolean?,
          @SerializedName("email") var email: String?,
          @SerializedName("createdAt") var createdAt: String?,
          @SerializedName("updatedAt") var updatedAt: String?,
          @SerializedName("name") var name: String?,
          @SerializedName("role") var role: String?
  )
}