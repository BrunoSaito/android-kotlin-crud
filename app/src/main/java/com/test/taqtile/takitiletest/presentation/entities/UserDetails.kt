package com.test.taqtile.takitiletest.presentation.entities



data class UserDetails (
        val data: Data?
) {

  data class Data(
          val id: Int?,
          val active: Boolean?,
          val email: String?,
          val createdAt: String?,
          val updatedAt: String?,
          val name: String?,
          val role: String?
  )
}