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
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.RetrofitInitializer
import com.test.taqtile.takitiletest.DataModels.User
import com.test.taqtile.takitiletest.Utils
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.list_row.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserListActivity : AppCompatActivity() {

  private var name: String? = null
  private var token: String? = null

  private var preferences: HashMap<String, String>? = null

  private var listCompleteData =  ArrayList<User.Data>()
  private var listUsers = ArrayList<User.Data>()
  private val progressBarListView: ProgressBar by lazy {
    val progressBarView = LayoutInflater.from(this).inflate(R.layout.bottom_listview_progressbar, null)
    progressBarView.findViewById<ProgressBar>(R.id.progressBarListView)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_list)

    preferences = Utils(this@UserListActivity).getPreferences()
    name = preferences!!.get("name")
    token = preferences!!.get("token")

    val jsonParams = JSONObject()
    jsonParams.put("page", "0")
    jsonParams.put("window", "100")

    getUsersList(jsonParams)

    listViewUsers.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
      val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)

      intent.putExtra("id", listCompleteData.get(position).id.toString())
      startActivity(intent)
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
    }
  }

  private fun getUsersList(jsonParams: JSONObject) {
    val users = RetrofitInitializer(token).userServices().listUsers(jsonParams)

    users.enqueue(object : Callback<User?> {
      override fun onResponse(call: Call<User?>?, response: Response<User?>?) {
        listCompleteData = response!!.body()!!.data

        for (i in 0..9) {
          listUsers.add(listCompleteData.get(i))
        }

        val newToken = response.headers().get("Authorization")
        Utils(this@UserListActivity).saveNewToken(newToken)

        listViewUsers!!.addFooterView(progressBarListView)

        val adapter = UsersAdapter(this@UserListActivity, listUsers)
        listViewUsers!!.adapter = adapter
        setListViewOnScrollListener()
      }
      override fun onFailure(call: Call<User?>?, t: Throwable?) {

      }
    })
  }

  private fun setListViewOnScrollListener(){
      listViewUsers!!.setOnScrollListener(object: AbsListView.OnScrollListener{
        override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {}

        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
          if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                            listViewUsers!!.lastVisiblePosition == listUsers.size &&
                            listViewUsers!!.lastVisiblePosition < listCompleteData.size) {
            progressBarListView.visibility = ProgressBar.VISIBLE
            addMoreItems()
          }
        }
      })
  }

  private fun addMoreItems(){
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
        Log.i("JSA", "set Tag for ViewHolder, position: " + position)
      } else {
        view = convertView
        vh = view.tag as ViewHolder
      }

      vh.listViewUserName.text = usersList[position].name
      vh.listViewUserRole.text = usersList[position].role

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
    val listViewUserName: TextView
    val listViewUserRole: TextView

    init {
      this.listViewUserName = view!!.listViewUserName
      this.listViewUserRole = view.listViewUserRole
    }
  }
}
