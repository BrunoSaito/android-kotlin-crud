package com.test.taqtile.takitiletest.domain.entities


data class UserEntity (
  var data: List<Data>? = null,
  var pagination: Pagination? = null
) {

  data class Pagination(
    var page: Int?,
    var window: Int?,
    var total: Int?,
    var totalPages: Int?
  )

  data class Data(
    var id: Int?,
    var active: Boolean?,
    var email: String?,
    var createdAt: String?,
    var updatedAt: String?,
    var name: String?,
    var role: String?
  )
}