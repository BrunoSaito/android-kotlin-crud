package com.test.taqtile.takitiletest.data

import android.content.Context
import android.util.Log
import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.core.config.Constants
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class ResponseValidator @Inject constructor(private val context: Context) {

  fun <T> convertResultToObservableModel(result: Result<T>): Observable<T> {
    if (isSuccessfulResult(result)) {
      val userToken = result.response()?.headers()?.get(Constants.AUTHORIZATION_HEADER)

      if (userToken != null) {
        Toolbox.userSharedPreferences.saveAccessToken(userToken)
      }

      return Observable.just(result.response()?.body())
    }

    Log.d("D",  "mensagem de response :  ${result.response()}")
    Log.d("D",  "mensagem de error : ${result.error()}")
//    return Observable.error(getRemoteError(result))
      return Observable.error(Throwable("error"))
  }

  private fun isSuccessfulResult(result: Result<*>): Boolean {
    val response = result.response()
    if (response != null)
      return response.isSuccessful
    else
      return false
  }
}