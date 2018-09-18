package com.test.taqtile.takitiletest.data.respositories


//class RemoteUserDataStore(private val userServices: UserServices) : UserDataStore {
//  private val userDataMapper = UserDataEntityMapper()
//  private val detailsDataMapper = UserDetailsDataEntityMapper()
//
//  override fun getUsers(pagination: JSONObject): Observable<List<UserEntity>> {
//    return userServices.listUsers(pagination).map { results ->
//      results.users.map { userDataMapper.mapFrom(it) }
//    }
//  }
//
//  override fun getDetails(userId: Int): Observable<UserDetailsEntity> {
//    return userServices.getDetails(userId.toString()).flatMap { result ->
//      Observable.just(detailsDataMapper.mapFrom(result))
//    }
//  }
//}