package widgets.components.account

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.models.User
import com.test.taqtile.takitiletest.models.UserCreate
import kotlinx.android.synthetic.main.component_user_form.view.*

class ComponentUserForm(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

  private val spinnerItems = HashMap<String, String>()

  init {
    LayoutInflater.from(context).inflate(R.layout.component_user_form, this, true)
    orientation = VERTICAL
    setupSpinner()
  }

  private fun setupSpinner() {
    //TODO create a enum class that maps roles
    spinnerItems["Usuário"] = "user"
    spinnerItems["Administrador"] = "admin"

    val spinnerAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems.keys.toTypedArray())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    component_user_form_spinner_role.adapter = spinnerAdapter
  }

  fun getUserInfo(): UserCreate {
    return UserCreate(
            component_user_form_name.getInputText(),
            component_user_form_password.getInputText(),
            component_user_form_email.getInputText(),
            spinnerItems[component_user_form_spinner_role.selectedItem.toString()]
    )
  }

  fun setUserInfo(user: User) {
    component_user_form_name.setInputText(user.name)
    component_user_form_email.setInputText(user.email)
    component_user_form_spinner_role.setSelection(spinnerItems.values.indexOf(user.role))
  }
}