package com.test.taqtile.takitiletest.Activities

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.User
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.list_row.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset


class UserListActivity : AppCompatActivity() {

  private var name: String? = null
  private var token: String? = null
  private var sharedPrefs: SharedPreferences? = null


  private val url = "https://tq-template-server-sample.herokuapp.com/users?pagination={\"page\": 0 , \"window\": 100}"
  private val LOGIN_NAME = "LOGIN_NAME"
  private val LOGIN_TOKEN = "LOGIN_TOKEN"
  private val PREFS_FILENAME = "com.test.taqtile.takitiletest.prefs"

  private val listCompleteData = ArrayList<User>()
  private val listUsers = ArrayList<User>()
  private val progressBarListView: ProgressBar by lazy {
    val progressBarView = LayoutInflater.from(this).inflate(R.layout.bottom_listview_progressbar, null)
    progressBarView.findViewById<ProgressBar>(R.id.progressBarListView)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_list)

    getPreferences()

    val queue = Volley.newRequestQueue(this)

    val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
          Response.Listener { response ->
            Log.d("D", "response: " + response.toString(1))
            val responseArray: JSONArray = response.getJSONArray("data")

            for (i in 0..responseArray.length()-1) {
              val json = JSONObject(responseArray[i].toString())
              listCompleteData.add(User(json.getString("name"),
                      json.getString("role")))
            }

            for (i in 0..9)
              listUsers.add(listCompleteData[i])

            listViewUsers!!.addFooterView(progressBarListView)

            val adapter = UsersAdapter(this, listUsers)
            listViewUsers!!.adapter = adapter
            setListViewOnScrollListener()
          },
          Response.ErrorListener { error ->
            val networkError: NetworkResponse? = error.networkResponse

            val jsonErrorString = String(
                    networkError!!.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(networkError.headers)))

            val jsonError = JSONObject(jsonErrorString)
            val jsonErrorMessage = JSONObject(jsonError.getJSONArray("errors").getString(0))

            val builder = AlertDialog.Builder(this@UserListActivity)

            builder.setTitle("Erro no login.")
            builder.setMessage(jsonErrorMessage.getString("message"))
            builder.setNeutralButton("Ok") { _, _ -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()
          })
          {
            override fun getParams(): Map<String, String> {
              val params = HashMap<String, String>()
              params.put("page", "0")
              params.put("window", "100")

              return params
            }
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
              val headers = HashMap<String, String>()
              headers.put("Authorization", token.toString())

              return headers
            }
          }

    queue.add(jsonObjectRequest)
  }

  private fun getPreferences() {
    sharedPrefs = this.getSharedPreferences(PREFS_FILENAME, 0)
    name = sharedPrefs!!.getString(LOGIN_NAME, "")
    token = sharedPrefs!!.getString(LOGIN_TOKEN, "")
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
    private var usersList = ArrayList<User>()
    private var context: Context? = null

    constructor(context: Context, usersList: ArrayList<User>) : super() {
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
