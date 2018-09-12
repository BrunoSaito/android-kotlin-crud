package com.test.taqtile.takitiletest.domain.entities


data class UserEntity (
  val data: ArrayList<Data>?,
  val pagination: Pagination?
) {

  data class Pagination(
    val page: Int?,
    val window: Int?,
    val total: Int?,
    val totalPages: Int?
  )

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