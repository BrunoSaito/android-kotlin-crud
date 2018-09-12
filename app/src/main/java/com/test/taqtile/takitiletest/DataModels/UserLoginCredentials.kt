package com.test.taqtile.takitiletest.DataModels

data class UserLoginCredentials (
  val email: String,
  val password: String,
  val rememberMe: Boolean
)