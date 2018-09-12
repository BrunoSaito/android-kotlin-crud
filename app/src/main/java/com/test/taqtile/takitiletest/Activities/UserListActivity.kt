package com.test.taqtile.takitiletest.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
  private var listCompleteData =  ArrayList<User.Data>()
  private var listUsers = ArrayList<User.Data>()

  private val jsonParams: JSONObject? = JSONObject()
  private val progressBarListView: ProgressBar by lazy {
    val progressBarView = LayoutInflater.from(this).inflate(R.layout.bottom_listview_progressbar, null)
    progressBarView.findViewById<ProgressBar>(R.id.progressBarListView)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_list)

    preferences = Preferences(this@UserListActivity).getPreferences()
    name = preferences?.get("name")
    token = preferences?.get("token")

    jsonParams?.put("page", "0")
    jsonParams?.put("window", "100")

    getUsersList(jsonParams, "")

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
        getUsersList(jsonParams, query)

        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        getUsersList(jsonParams, newText)

        return true
      }
    })

    progressBarListUsers.visibility = ProgressBar.VISIBLE
  }

  private fun getUsersList(jsonParams: JSONObject?, query: String?) {
    val users = RetrofitInitializer(token).userServices().listUsers(jsonParams)

    users.enqueue(object : Callback<User?> {
      override fun onResponse(call: Call<User?>?, response: Response<User?>?) {
        listCompleteData = response!!.body()!!.data!!
        listUsers.clear()

        if (query.isNullOrEmpty()) {
          for (i in 0..9)
            listUsers.add(listCompleteData[i])
        }
        else {
          var i = 0
          while (listUsers.size < 10 && i < listCompleteData.size) {
            if (listCompleteData[i].name?.contains(query.toString())!!)
              listUsers.add(listCompleteData[i])
            i++
          }
        }

        val newToken = response.headers().get("Authorization")
        Preferences(this@UserListActivity).saveNewToken(newToken)

        listViewUsers?.addFooterView(progressBarListView)

        val adapter = UsersAdapter(this@UserListActivity, listUsers)
        listViewUsers?.adapter = adapter
        setListViewOnScrollListener()

        progressBarListUsers.visibility = ProgressBar.GONE
      }
      override fun onFailure(call: Call<User?>?, failureResponse: Throwable) {
        progressBarListUsers.visibility = ProgressBar.GONE
      }
    })
  }

  private fun setListViewOnScrollListener(){
      listViewUsers!!.setOnScrollListener(object: AbsListView.OnScrollListener{
        override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {}

        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
          if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                            listViewUsers?.lastVisiblePosition!! == listUsers.size &&
                            listViewUsers?.lastVisiblePosition!! < listCompleteData.size) {
            progressBarListView.visibility = ProgressBar.VISIBLE
            addMoreItems()
          }
        }
      })
  }

  private fun addMoreItems() {
    val size = listUsers.size

    (0..10).filter { (size + it) < listCompleteData.size }
            .mapTo(listUsers) { listCompleteData[it + size] }

//        for (i in 0..10) {
//            if (size + i < listCompleteData.size) {
//                listUsers.add(listCompleteData[size+i])
//            }
//        }

    progressBarListView.visibility = ProgressBar.GONE
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
        intent.putExtra("userId", listCompleteData[position].id.toString())

        startActivity(intent)
        finish()
      }

      vh.buttonDeleteUser.setOnClickListener {
        val builder = AlertDialog.Builder(this@UserListActivity)
        builder.setMessage("Deseja realmente deletar o usuário " + userName + "?")
        builder.setPositiveButton("Sim") { _, _ ->
          this@UserListActivity.deleteUser(listUsers[position].id.toString())
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

  private fun deleteUser(userId: String?) {
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