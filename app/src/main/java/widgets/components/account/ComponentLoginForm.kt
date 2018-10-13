package widgets.components.account

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.test.taqtile.takitiletest.R
import com.test.taqtile.takitiletest.models.LoginCredentials
import kotlinx.android.synthetic.main.component_login_form.view.*

class ComponentLoginForm(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

  init {
    LayoutInflater.from(context).inflate(R.layout.component_login_form, this, true)
    orientation = VERTICAL
  }

  fun getLoginCredentials(): LoginCredentials {
    return LoginCredentials(
              component_login_form_email.getInputText(),
              component_login_form_password.getInputText(),
              false
           )
  }
}