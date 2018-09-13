package com.test.taqtile.takitiletest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Users")
data class UserData (
  @SerializedName("data") var data: List<Data>? = null,
  @SerializedName("pagination") var pagination: Pagination? = null
) {

  data class Pagination(
    @SerializedName("page") var page: Int?,
    @SerializedName("window") var window: Int?,
    @SerializedName("total") var total: Int?,
    @SerializedName("totalPages") var totalPages: Int?
  )

  data class Data(
    @SerializedName("id") @PrimaryKey var id: Int?,
    @SerializedName("active") var active: Boolean?,
    @SerializedName("email") var email: String?,
    @SerializedName("createdAt") var createdAt: String?,
    @SerializedName("updatedAt") var updatedAt: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("role") var role: String?
  )
}