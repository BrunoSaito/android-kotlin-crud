package com.test.taqtile.takitiletest.presentation.userDetails

import androidx.lifecycle.MutableLiveData
import com.test.taqtile.takitiletest.domain.common.Mapper
import com.test.taqtile.takitiletest.domain.entities.UserDetailsEntity
import com.test.taqtile.takitiletest.domain.usecases.GetUserDetails
import com.test.taqtile.takitiletest.presentation.common.BaseViewModel
import com.test.taqtile.takitiletest.presentation.entities.UserDetails


class UserDetailsViewModel(private val getUserDetails: GetUserDetails,
                           private val mapper: Mapper<UserDetailsEntity, UserDetails>,
                           private val userId: Int) : BaseViewModel() {

  lateinit var userDetailsEntity: UserDetailsEntity
  var viewState: MutableLiveData<UserDetailsViewState> = MutableLiveData()

  init {
    viewState.value = UserDetailsViewState()
  }

  fun getUserDetails() {
    addDisposable(
            getUserDetails.getDetails(userId)
                    .map {
                      it?.let {
                        userDetailsEntity = it
                        mapper.mapFrom(userDetailsEntity)
                      } ?: run {
                        throw Throwable("Something went wrong! :(")
                      }
                    }
                    .subscribe { onUserDetailsReceived(it) }
    )
  }

  fun onUserDetailsReceived(userDetails: UserDetails) {
    val newViewState = viewState.value?.copy(
            isLoading = false,
            userName = userDetails.data?.name,
            userEmail = userDetails.data?.email
    )

    viewState.value = newViewState
  }
}