package com.test.taqtile.takitiletest.presentation.userDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.mobileUI.model.UserDetailsViewModel
import javax.inject.Inject


//class UserDetailsActivity : AppCompatActivity() {
//
//  @Inject
//  lateinit var factory: UserDetailsVMFactory
//
//  private lateinit var userDetailsViewModel: UserDetailsViewModel
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_user_details)
//
//    factory.userId = 81
//    userDetailsViewModel = ViewModelProviders.of(this, factory).get(UserDetailsViewModel::class.java)
//    userDetailsViewModel.getUserDetails()
//  }
//}