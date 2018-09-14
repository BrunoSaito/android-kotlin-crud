package com.test.taqtile.takitiletest.mobileUI.model

data class UserDetailsViewModel (
        var data: Data? = null
) {
  data class Data (
          var id: Int?,
          var active: Boolean?,
          var email: String?,
          var createdAt: String?,
          var updatedAt: String?,
          var name: String?,
          var role: String?
  )
}