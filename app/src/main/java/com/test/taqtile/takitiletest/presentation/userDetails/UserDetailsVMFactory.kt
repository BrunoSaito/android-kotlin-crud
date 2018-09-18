package com.test.taqtile.takitiletest.presentation.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.usecases.GetUserDetails
import com.test.taqtile.takitiletest.mobileUI.model.UserDetailsViewModel
import com.test.taqtile.takitiletest.presentation.model.UserDetails


//class UserDetailsVMFactory(
//        private val getUserDetails: GetUserDetails,
//        private val mapper: Mapper<UserDetailsEntity, UserDetails>) : ViewModelProvider.Factory {
//
//  var userId: Int = -1
//
//  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//    return UserDetailsViewModel(
//            getUserDetails,
//            mapper,
//            userId) as T //TODO: solve casting issue
//  }
//}