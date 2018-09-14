package com.test.taqtile.takitiletest.mobileUI.userDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.user_details.view.*
import javax.inject.Inject


class UserDetailsAdapter @Inject constructor(): RecyclerView.Adapter<UserDetailsAdapter.ViewHolder>() {

  val userName: String? = null
  val userEmail: String? = null
  val userRole: String? = null

  override fun getItemCount(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onBindViewHolder(holder: UserDetailsAdapter.ViewHolder, position: Int) {
    holder.userDetailsName.text = userName
    holder.userDetailsEmail.text = userEmail
    holder.userDetailsRole.text = userRole
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailsAdapter.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.user_details, parent, false)

    return ViewHolder(view)
  }

  inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val userDetailsName = view.userDetailsName
    val userDetailsEmail = view.userDetailsEmail
    val userDetailsRole = view.userDetailsRole
  }
}