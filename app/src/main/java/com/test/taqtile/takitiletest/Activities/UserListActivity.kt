package com.test.taqtile.takitiletest.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.test.taqtile.takitiletest.DataModels.DeleteUserSuccess
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.RetrofitInitializer
import com.test.taqtile.takitiletest.DataModels.User
import com.test.taqtile.takitiletest.Preferences
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.list_row.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserListActivity : AppCompatActivity() {

  private var name: String? = null
  private var token: String? = null

  private var preferences: HashMap<String?, String?>? = null
  private var listRequest =  ArrayList<User.Data>()
  private var listUsers = ArrayList<User.Data>()
  private var page = 0
  private var window = 10
  private var total: Int? = null
  private var lastPosition = 5

  private val jsonParams: JSONObject? = JSONObject()
  private val progressBarListView: ProgressBar by lazy {
    val progressBarView = LayoutInflater.from(this).inflate(R.layout.bottom_listview_progressbar, null)
    progressBarView.findViewById<ProgressBar>(R.id.progressBarListView)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_list)

    listViewUsers?.addFooterView(progressBarListView)

    preferences = Preferences(this@UserListActivity).getPreferences()
    name = preferences?.get("name")
    token = preferences?.get("token")

    jsonParams?.put("page", page.toString())
    jsonParams?.put("window", window.toString())

    getUsersListRequest(jsonParams, "")

    val actionBar = supportActionBar
    actionBar?.title = getString(R.string.user_list_title)

    listViewUsers.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
      val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)
      intent.putExtra("userId", listUsers[position].id.toString())

      startActivity(intent)
      finish()
    }

    if (!intent.getStringExtra("message").isNullOrEmpty()) {
      val builder = AlertDialog.Builder(this@UserListActivity)
      builder.setTitle("Mensagem")
      builder.setMessage(intent.getStringExtra("message"))
      builder.setNeutralButton("OK") { _, _ -> }

      val dialog = builder.create()
      dialog.show()
    }

    fabCreateNewUser.setOnClickListener {
      val intent = Intent(this@UserListActivity, CreateNewUserActivity::class.java)

      startActivity(intent)
      finish()
    }

    searchListView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        jsonParams?.put("page", "0")
        jsonParams?.put("window", total.toString())

        getUsersListRequest(jsonParams, query)

        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        jsonParams?.put("page", "0")
        jsonParams?.put("window", total.toString())

        getUsersListRequest(jsonParams, newText)

        return true
      }
    })
  }

  private fun getUsersListRequest(jsonParams: JSONObject?, query: String?) {
    progressBarListView.visibility = ProgressBar.VISIBLE
    val users = RetrofitInitializer(token).userServices().listUsers(jsonParams)

    users.enqueue(object : Callback<User?> {
      override fun onResponse(call: Call<User?>?, response: Response<User?>?) {
        listRequest = response!!.body()!!.data!!
        total = response.body()!!.pagination!!.total

        if (query.isNullOrEmpty()) {
          listUsers.addAll(listRequest)
        }
        else {
          page = 0

          listUsers.clear()
          listUsers = ArrayList(listRequest
                                .filter { it.name?.contains(query.toString()) ?: false })
        }

        val newToken = response.headers().get("Authorization")
        Preferences(this@UserListActivity).saveNewToken(newToken)

        val adapter = UsersAdapter(this@UserListActivity, listUsers)
        listViewUsers?.adapter = adapter
        setListViewOnScrollListener()

        listViewUsers.setSelection(lastPosition.minus(6))

        progressBarListView.visibility = ProgressBar.GONE
        progressBarListUsers.visibility = ProgressBar.GONE
      }
      override fun onFailure(call: Call<User?>?, failureResponse: Throwable) {
        progressBarListView.visibility = ProgressBar.GONE
        progressBarListUsers.visibility = ProgressBar.GONE
      }
    })
  }

  private fun setListViewOnScrollListener(){
      listViewUsers!!.setOnScrollListener(object: AbsListView.OnScrollListener{
        override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {}

        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
          if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                            listViewUsers?.lastVisiblePosition!! >= listUsers.size &&
                            listUsers.size < total!!) {
            page = page.plus(1)
            jsonParams?.put("page", page.toString())
            lastPosition = listViewUsers.lastVisiblePosition
            getUsersListRequest(jsonParams, "")
          }
        }
      })
  }

  inner class UsersAdapter : BaseAdapter {
    private var usersList = ArrayList<User.Data>()
    private var context: Context? = null

    constructor(context: Context, usersList: ArrayList<User.Data>) : super() {
      this.usersList = usersList
      this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
      val view: View?
      val vh: ViewHolder

      if (convertView == null) {
        view = layoutInflater.inflate(R.layout.list_row, parent, false)
        vh = ViewHolder(view)
        view!!.tag = vh
      } else {
        view = convertView
        vh = view.tag as ViewHolder
      }

      val userName = usersList[position].name
      val userEmail = usersList[position].email
      val userRole = usersList[position].role

      vh.listViewUserName.text = userName
      vh.listViewUserRole.text = userRole

      vh.buttonEditUser.setOnClickListener {
        val intent = Intent(this@UserListActivity, EditUserActivity::class.java)
        intent.putExtra("userName", userName)
        intent.putExtra("userEmail", userEmail)
        intent.putExtra("userRole", userRole)
        intent.putExtra("userId", listRequest[position].id.toString())

        startActivity(intent)
        finish()
      }

      vh.buttonDeleteUser.setOnClickListener {
        val builder = AlertDialog.Builder(this@UserListActivity)
        builder.setMessage("Deseja realmente deletar o usuário " + userName + "?")
        builder.setPositiveButton("Sim") { _, _ ->
          this@UserListActivity.deleteUserRequest(listUsers[position].id.toString())
        }
        builder.setNegativeButton("Não") { dialog, _ ->
          dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
      }

      vh.buttonDeleteUser.isFocusable = false
      vh.buttonDeleteUser.isFocusableInTouchMode = false
      vh.buttonDeleteUser.isClickable = true
      vh.buttonEditUser.isFocusable = false
      vh.buttonEditUser.isFocusableInTouchMode = false
      vh.buttonEditUser.isClickable = true

      return view
    }

    override fun getItem(position: Int): Any {
      return usersList[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
      return usersList.size
    }
  }

  private class ViewHolder(view: View?) {
    val listViewUserName: TextView = view?.listViewUserName!!
    val listViewUserRole: TextView = view?.listViewUserRole!!
    val buttonEditUser: ImageButton = view?.buttonEditUser!!
    val buttonDeleteUser: ImageButton = view?.buttonDeleteUser!!
  }

  private fun deleteUserRequest(userId: String?) {
    val userDelete = RetrofitInitializer(token).userServices().deleteUser(userId)

    userDelete.enqueue(object: Callback<DeleteUserSuccess?> {
      override fun onResponse(call: Call<DeleteUserSuccess?>?, response: Response<DeleteUserSuccess?>?) {
        try {
          if (response!!.body()!!.data!!.active == false) {
            val intent = Intent(this@UserListActivity, UserListActivity::class.java)

            intent.putExtra("message", "Usuário deletado com sucesso!")
            startActivity(intent)
            finish()
          }
        }
        catch (e: Exception) {

        }
      }

      override fun onFailure(call: Call<DeleteUserSuccess?>?, failureResponse: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    })
  }
}