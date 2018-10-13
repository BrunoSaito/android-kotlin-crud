package com.test.taqtile.takitiletest.services

import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.core.Toolbox
import com.test.taqtile.takitiletest.data.CredentialsInterceptor
import com.test.taqtile.takitiletest.data.account.AccountRemoteDataSource
import com.test.taqtile.takitiletest.data.account.AccountRepository
import com.test.taqtile.takitiletest.domain.LoginUseCase
import io.reactivex.disposables.Disposable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by taqtile on 10/29/15.
 */
// TODO - finish this class! Use retrofit on everything
class APIRequest private constructor() {
  private var loginUseCase: LoginUseCase? = null
  private var accountRemoteDataSource: AccountRemoteDataSource? = null
  private var accountRepository: AccountRepository? = null
  private val disposables = ArrayList<Disposable>()
  //endregion

  //region node-http-connector objects
//  private var connector: Connector? = null
//  private var jwtAuthStrategy: JWTAuthStrategy? = null
//  private var versionStrategy: VersionStrategy? = null
//  private var connectorRequestFactory: ConnectorRequestFactory? = null
//  private var contextStrategy: ConnectorContextStrategy? = null

  private val authToken: String?
    get() = Toolbox.userSharedPreferences.getAccessToken()

//  init {
//    if (instance != null) {
//      DevelopmentExceptionHelper().throwException("Use getInstance() method to get the single instance of this class.")
//    }
//  }

  //region Request methods
//  fun performRequest(method: HTTPMethod, path: String, jsonParams: String, authenticated: Boolean, responseHandler: RequestHandler) {
//    val headers = HashMap<String, String>()
//
//
//    var connectorRequest: ConnectorRequest? = null
//
//    when (method) {
//      POST -> connectorRequest = getConnectorRequestFactory().createPOSTConnectorRequest(path, headers, jsonParams)
//      GET -> connectorRequest = getConnectorRequestFactory().createGETConnectorRequest(path, headers, jsonParams)
//      PUT -> connectorRequest = getConnectorRequestFactory().createPUTConnectorRequest(path, headers, jsonParams)
//      DELETE -> connectorRequest = getConnectorRequestFactory().createDELETEConnectorRequest(path, headers, jsonParams)
//    }
//
//    performRequest(connectorRequest, authenticated, responseHandler)
//  }
//
//  private fun performRequest(request: ConnectorRequest?, authenticated: Boolean?, responseHandler: RequestHandler?) {
//    if (authenticated!! && authToken != null) {
//      request!!.setHeader(Constants.RAIA_AUTH_HEADER, "Bearer " + authToken!!)
//    }
//
//    if (request!!.getContext() == null) {
//      request!!.setContext(RaiaApplication.getContext())
//    }
//
//    getConnector().performRequest(request, object : IRequestHandler() {
//      fun onSuccess(s: String) {
//        responseHandler!!.completion(s)
//      }
//
//      fun onFailure(connectorError: ConnectorError) {
//        var connectorError = connectorError
//        if (connectorError.getErrorType() === ConnectorErrorType.NoConnection) {
//          responseHandler!!.connectionFailure(connectorError)
//        } else if (authenticated && connectorError.getErrorType() === ConnectorErrorType.Unauthorized && UserHelper.isUserSignedIn()) {
//          // if the error is unauthorized but the user is logged, performs auto login
//          performAutoLogin(request, responseHandler, connectorError)
//        } else if (connectorError.getErrorType() === ConnectorErrorType.Unknown && responseHandler != null) {
//          connectorError = ConnectorError(ConnectorErrorType.Unknown, RaiaApplication.getContext().getString(R.string.tq_connection_error))
//          responseHandler!!.failure(connectorError)
//        } else if (responseHandler != null) {
//          responseHandler!!.failure(connectorError)
//        }
//      }
//    })
//  }
//
//  fun cancelAllRequests() {
//    if (disposables.size > 0) {
//      for (disposable in disposables) {
//        disposable.dispose()
//      }
//    }
//
//    getConnector().cancelAllRequests()
//  }

  companion object {
    //region Singleton defs
    @Volatile
    private var instance: APIRequest? = null

    fun getInstance(): APIRequest? {
      if (instance == null) {
        synchronized(APIRequest::class.java) {
          instance = APIRequest()
//          instance!!.authenticationRemoteDataSource = AuthenticationRemoteDataSource(RemoteInterceptor())
//          instance!!.authenticationRepository = AuthenticationRepository(instance!!.authenticationRemoteDataSource)
//          instance!!.cartRemoteDataSource = CartRemoteDataSource(RemoteInterceptor())
//          instance!!.cartRepository = CartRepository(instance!!.cartRemoteDataSource)
          val CredentialsInterceptor = CredentialsInterceptor()
          instance!!.accountRemoteDataSource = AccountRemoteDataSource(CredentialsInterceptor())
          instance!!.accountRepository = AccountRepository(instance!!.accountRemoteDataSource!!)
//          instance!!.remoteInterceptor = RemoteInterceptor()
//          instance!!.authenticatedInterceptor = RemoteAuthenticatedInterceptor()
//          instance!!.remoteAuthenticator = RemoteAuthenticator(instance!!.authenticatedInterceptor, instance!!.authenticationRepository, RaiaApplication.getContext())
//          instance!!.userInfoUseCase = GetUserInfoUseCase(instance!!.accountRepository)
//          instance!!.userPropertiesAnalytics = UserPropertiesAnalytics()
//          instance!!.listAddressesUseCase = ListAddressesUseCase(instance!!.addressRepository, instance!!.userPropertiesAnalytics)
          instance!!.loginUseCase = LoginUseCase(instance!!.accountRepository!!)
        }
      }

      return instance
    }
  }
  //endregion
}
