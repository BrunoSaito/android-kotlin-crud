package com.test.taqtile.takitiletest.presentation.userDetails


data class UserDetailsViewState (
  var isLoading: Boolean? = true,
  var userName: String? = "",
  var userEmail: String? = "",
  var userRole: String? = ""
)