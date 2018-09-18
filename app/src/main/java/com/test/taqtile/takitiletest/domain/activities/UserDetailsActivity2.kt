//package com.test.taqtile.takitiletest.domain.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.ProgressBar
//import androidx.appcompat.app.AlertDialog
//import com.test.taqtile.takitiletest.R
//import kotlinx.android.synthetic.main.activity_user_details.*
//
//
//class UserDetailsActivity2 : AppCompatActivity() {
//
//  private var userId: String? = null
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_user_details)
//
//    val actionBar = supportActionBar
//    actionBar?.title = getString(R.string.user_details_title)
//
//    userId = intent.getStringExtra("userId")
////    getDetailsRequest(Preferences.token, userId)
//
//    buttonUserDetailsEdit.setOnClickListener {
//      val intent = Intent(this@UserDetailsActivity2, EditUserActivity::class.java)
//
//      intent.putExtra("userName", userDetailName.text)
//      intent.putExtra("userEmail", userDetailEmail.text)
//      intent.putExtra("userRole", userDetailRole.text)
//      intent.putExtra("userId", userId)
//
//      startActivity(intent)
//      finish()
//    }
//
//    buttonUserDetailsDelete.setOnClickListener {
//      val builder = AlertDialog.Builder(this@UserDetailsActivity2)
//      builder.setMessage("Deseja realmente deletar o usuário " + userDetailName.text + "?")
//      builder.setPositiveButton("Sim") { _, _ ->
////        deleteUser(userId)
//      }
//      builder.setNegativeButton("Não") { dialog, _ ->
//        dialog.dismiss()
//      }
//
//      val dialog = builder.create()
//      dialog.show()
//    }
//
//    if (!intent.getStringExtra("message").isNullOrEmpty()) {
//      val builder = AlertDialog.Builder(this@UserDetailsActivity2)
//      builder.setTitle("Mensagem")
//      builder.setMessage(intent.getStringExtra("message"))
//      builder.setNeutralButton("OK") { _, _ -> }
//
//      val dialog = builder.create()
//      dialog.show()
//    }
//
//    progressBarDetailsUser.visibility = ProgressBar.VISIBLE
//  }
//
//  override fun onBackPressed() {
////    val intent = Intent(this@UserDetailsActivity2, UserListActivity::class.java)
////
////    startActivity(intent)
////    finish()
//  }
////
////  private fun getDetailsRequest(token: String?, userId: String?) {
////    val userDetails = RetrofitInitializer(Preferences.token).userServices().getDetails(userId)
////
////    userDetails.enqueue(object : Callback<UserDetails?> {
////      override fun onResponse(call: Call<UserDetails?>, response: Response<UserDetails?>) {
////        userDetailName.text = response.body()!!.data!!.name
////        userDetailEmail.text = response.body()!!.data!!.email
////        userDetailRole.text = response.body()!!.data!!.role
////
////        progressBarDetailsUser.visibility = ProgressBar.GONE
////      }
////
////      override fun onFailure(call: Call<UserDetails?>, failureResponse: Throwable) {
////        progressBarDetailsUser.visibility = ProgressBar.GONE
////      }
////    })
////  }
////
////  private fun deleteUser(userId: String?) {
////    val userDelete = RetrofitInitializer(Preferences.token).userServices().deleteUser(userId)
////
////    userDelete.enqueue(object: Callback<DeleteUserSuccess?> {
////      override fun onResponse(call: Call<DeleteUserSuccess?>?, response: Response<DeleteUserSuccess?>?) {
////        try {
////          if (response!!.body()!!.data!!.active == false) {
////            val intent = Intent(this@UserDetailsActivity2, UserListActivity::class.java)
////
////            intent.putExtra("message", "Usuário deletado com sucesso!")
////            startActivity(intent)
////            finish()
////          }
////        }
////        catch (e: Exception) {
////
////        }
////      }
////
////      override fun onFailure(call: Call<DeleteUserSuccess?>?, failureResponse: Throwable) {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////      }
////    })
////  }
//}
