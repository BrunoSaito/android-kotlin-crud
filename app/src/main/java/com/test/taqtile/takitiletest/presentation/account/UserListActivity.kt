package com.test.taqtile.takitiletest.presentation.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.domain.ListUsersUseCase
import com.test.taqtile.takitiletest.models.ListUserResponse
import com.test.taqtile.takitiletest.models.User
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_list.*
import org.json.JSONObject
import widgets.lists.user.UsersAdapter
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import com.test.taqtile.takitiletest.core.config.Constants

class UserListActivity : AppCompatActivity(), UsersAdapter.Listener {

  @Inject
  lateinit var listUsersUseCase: ListUsersUseCase

  private var disposables: MutableList<Disposable> = mutableListOf()
  private var adapter: UsersAdapter? = null

  private var users = ArrayList<User>()
  private var page = 0
  private var window = 10
  private var totalPages: Int? = null

  private val jsonParams: JSONObject = JSONObject()

  // region listeners
  override fun onUserSelected(user: User?) {
    val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)
    intent.putExtra(Constants.USER_ID, user?.id.toString())

    startActivity(intent)
    finish()
  }

  override fun onEditUserClicked(user: User?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onDeleteUserClicked(user: User?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  // end region

  // region lifecycle
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_list)
    AndroidInjection.inject(this)

    jsonParams.put("page", page.toString())
    jsonParams.put("window", window.toString())

    list(jsonParams)

    setupActionBar()
    setupRecycler()
    setupFabCreateButton()

    if (!intent.getStringExtra("message").isNullOrEmpty()) {
      val builder = AlertDialog.Builder(this@UserListActivity)
      builder.setTitle("Mensagem")
      builder.setMessage(intent.getStringExtra("message"))
      builder.setNeutralButton("OK") { _, _ -> }

      val dialog = builder.create()
      dialog.show()
    }

    searchListView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        jsonParams.put("page", "0")
        jsonParams.put("window", totalPages.toString())

//        getUsersListRequest(jsonParams, query)

        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        jsonParams.put("page", "0")
        jsonParams.put("window", totalPages.toString())

//        getUsersListRequest(jsonParams, newText)

        return true
      }
    })
  }
  // end region

  // region setup
  private fun setupActionBar() {
    val actionBar = supportActionBar
    actionBar?.title = getString(R.string.user_list_title)
  }

  private fun setupRecycler() {
    val manager = LinearLayoutManager(this@UserListActivity)
    user_list_recycler.setHasFixedSize(true)
    user_list_recycler.layoutManager = manager
    adapter = UsersAdapter(this)
    user_list_recycler.adapter = adapter

    setupRecyclerOnScrollListener()
  }

  private fun setupFabCreateButton() {
    fabCreateNewUser.setOnClickListener {
      val intent = Intent(this@UserListActivity, UserFormActivity::class.java)

      startActivity(intent)
      finish()
    }
  }

  private fun setupRecyclerOnScrollListener() {
    user_list_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (!recyclerView.canScrollVertically(1)) {
          page = page.plus(1)
          jsonParams.put("page", page.toString())

          list(jsonParams)
        }
      }
    })
  }
  // end region

  // region services
  private fun list(pagination: JSONObject, query: String? = null) {
    disposables.add(
            listUsersUseCase.execute(pagination)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                      onSuccess(it)
                    }, {
                      onFailure(it.message)
                    })
    )
  }
  // end region

  // region private
  private fun onSuccess(listUserResponse: ListUserResponse) {
    listUserResponse.data.map { users.add(it) }
    totalPages = listUserResponse.pagination.totalPages

    runOnUiThread {
      adapter?.users = users
      adapter?.notifyDataSetChanged()
    }

    progressBarListUsers.visibility = ProgressBar.GONE
  }

  private fun onFailure(message: String?) {
    progressBarListUsers.visibility = ProgressBar.GONE
  }
  // end region

//  inner class UsersAdapter : BaseAdapter {
//    private var usersList = ArrayList<User>()
//    private var context: Context? = null
//
//    constructor(context: Context, usersList: ArrayList<User>) : super() {
//      this.usersList = usersList
//      this.context = context
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//      val view: View?
//      val vh: ViewHolder
//
//      if (convertView == null) {
//        view = layoutInflater.inflate(R.layout.user_list_row, parent, false)
//        vh = ViewHolder(view)
//        view!!.tag = vh
//      } else {
//        view = convertView
//        vh = view.tag as ViewHolder
//      }
//
//      val userName = usersList[position].name
//      val userEmail = usersList[position].email
//      val userRole = usersList[position].role
//
//      vh.listViewUserName.text = userName
//      vh.listViewUserRole.text = userRole
//
//      vh.buttonEditUser.setOnClickListener {
//        val intent = Intent(this@UserListActivity, UserEditActivity::class.java)
//        intent.putExtra("userName", userName)
//        intent.putExtra("userEmail", userEmail)
//        intent.putExtra("userRole", userRole)
//        intent.putExtra("userId", listRequest[position].id.toString())
//
//        startActivity(intent)
//        finish()
//      }
//
//      vh.buttonDeleteUser.setOnClickListener {
//        val builder = AlertDialog.Builder(this@UserListActivity)
//        builder.setMessage("Deseja realmente deletar o usuário " + userName + "?")
//        builder.setPositiveButton("Sim") { _, _ ->
////          this@UserListActivity.deleteUserRequest(users[position].id.toString())
//        }
//        builder.setNegativeButton("Não") { dialog, _ ->
//          dialog.dismiss()
//        }
//
//        val dialog = builder.create()
//        dialog.show()
//      }
//
//      vh.buttonDeleteUser.isFocusable = false
//      vh.buttonDeleteUser.isFocusableInTouchMode = false
//      vh.buttonDeleteUser.isClickable = true
//      vh.buttonEditUser.isFocusable = false
//      vh.buttonEditUser.isFocusableInTouchMode = false
//      vh.buttonEditUser.isClickable = true
//
//      return view
//    }
//
//    override fun getItem(position: Int): Any {
//      return usersList[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//      return position.toLong()
//    }
//
//    override fun getCount(): Int {
//      return usersList.size
//    }
//  }
//
//  private class ViewHolder(view: View?) {
//    val listViewUserName: TextView = view?.user_row_user_name!!
//    val listViewUserRole: TextView = view?.user_row_user_role!!
//    val buttonEditUser: ImageButton = view?.`@+id/user_row+button_edit`!!
//    val buttonDeleteUser: ImageButton = view?.user_row_button_delete!!
//  }

//  private fun deleteUserRequest(userId: String?) {
//    val userDelete = RetrofitInitializer(Preferences.token).userServices().deleteUser(userId)
//
//    userDelete.enqueue(object: Callback<DeleteUserSuccess?> {
//      override fun onResponse(call: Call<DeleteUserSuccess?>?, response: Response<DeleteUserSuccess?>?) {
//        try {
//          if (response!!.body()!!.data!!.active == false) {
//            val intent = Intent(this@UserListActivity, UserListActivity::class.java)
//
//            intent.putExtra("message", "Usuário deletado com sucesso!")
//            startActivity(intent)
//            finish()
//          }
//        }
//        catch (e: Exception) {
//
//        }
//      }
//
//      override fun onFailure(call: Call<DeleteUserSuccess?>?, failureResponse: Throwable) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//      }
//    })
//  }
}