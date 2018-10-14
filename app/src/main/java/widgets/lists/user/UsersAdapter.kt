package widgets.lists.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.models.User
import kotlinx.android.synthetic.main.user_list_loader.view.*
import kotlinx.android.synthetic.main.user_list_row.view.*

class UsersAdapter(private val listener: Listener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  interface Listener {
    fun onUserSelected(id: String?)
    fun onEditUserClicked(id: String?)
    fun onDeleteUserClicked(id: String?, name: String?)
  }

  // region View types
  private val USER = 0
  private val LOADER = 1
  // end region

  var users: MutableList<User> = mutableListOf()

  // region RecyclerView Adapter
  override fun getItemCount(): Int {
    return users.size
  }

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      itemCount-1 -> LOADER
      else        -> USER
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      LOADER -> LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_loader, parent, false))
      else   -> UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_row, parent, false))
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val user = users[position]

    when (position) {
      itemCount-1 -> setupLoader(holder as LoaderViewHolder, listener)
      else        -> setupUser(holder as UserViewHolder, user, listener)
    }
  }
  // end region

  // region View Holders
  inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val clickableView = view.list_row
    val name = view.user_row_user_name
    val role = view.user_row_user_role
    val buttonDelete = view.user_row_button_delete
    val buttonEdit = view.user_row_button_edit
  }

  inner class LoaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val loader = view.user_list_progress_bar
  }
  // end region

  // region setup
  private fun setupUser(holder: UserViewHolder, user: User?, listener: Listener?) {
    holder.clickableView.setOnClickListener { listener?.onUserSelected(user?.id.toString()) }
    holder.buttonEdit.setOnClickListener { listener?.onEditUserClicked(user?.id.toString()) }
    holder.buttonDelete.setOnClickListener { listener?.onDeleteUserClicked(user?.id.toString(), user?.name) }
    holder.name.text = user?.name
    holder.role.text = user?.role
  }

  private fun setupLoader(holder: LoaderViewHolder, listener: Listener?) {
    holder.loader.visibility = View.VISIBLE
  }
  // end region
}