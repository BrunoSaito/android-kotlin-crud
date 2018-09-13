package com.test.taqtile.takitiletest.data.services


class ServiceGenerator {
  fun getUserServices() : UserServices {
    return RetrofitInitializer("").getRetrofit().create(UserServices::class.java)
  }
}
