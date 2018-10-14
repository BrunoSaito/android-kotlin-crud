package widgets.components.account

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.models.UserCreate
import kotlinx.android.synthetic.main.component_login_form.view.*
import kotlinx.android.synthetic.main.component_user_form.view.*

class ComponentUserForm(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

  init {
    LayoutInflater.from(context).inflate(R.layout.component_user_form, this, true)
    orientation = VERTICAL
  }

  fun getUser(): UserCreate {
    return UserCreate(
            component_user_form_name.getInputText(),
            component_user_form_password.getInputText(),
            component_login_form_email.getInputText(),
            component_user_form_spinner_role.selectedItem.toString()
    )
  }
}