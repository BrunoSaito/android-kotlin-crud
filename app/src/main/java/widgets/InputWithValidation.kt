package widgets

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.input_with_validation.view.*


class InputWithValidation (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

  private val MIN_PASSWORD_LENGTH = 4

  private var hint: String? = null
  private var errorMessage: String? = null
  private var inputType: String? = null
  private var inputText: String? = null

  private var colorPrimaryDark: Int? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.input_with_validation, this, true)
    orientation = VERTICAL
    colorPrimaryDark = getContext().getColor(R.color.color_primary_dark)

    attrs.let {
      val typedArray = context.obtainStyledAttributes(it, R.styleable.InputWithValidation, 0, 0)
      hint = resources.getText(typedArray
              .getResourceId(R.styleable.InputWithValidation_hint, R.string.default_empty)).toString()
      errorMessage = resources.getText(typedArray
              .getResourceId(R.styleable.InputWithValidation_error_message, R.string.default_invalid_field)).toString()
      inputType = resources.getText(typedArray
              .getResourceId(R.styleable.InputWithValidation_input_type, R.string.default_empty)).toString()
      inputText = resources.getText(typedArray
              .getResourceId(R.styleable.InputWithValidation_input_text, R.string.default_empty)).toString()

      setInputHint(hint)
      setErrorText(errorMessage)
      setInputType(inputType)
      setInputText(inputText)

      typedArray.recycle()
    }
  }

  fun getInputText(): String {
    return editText.text.toString()
  }

  fun setInputText(value: String?) {
    editText.setText(value, TextView.BufferType.EDITABLE)
  }

  fun setInputHint(value: String?) {
    editText.hint = value
  }

  fun setInputType(value: String?) {
    if (value.equals("email"))
      editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    if (value.equals("text"))
      editText.inputType = InputType.TYPE_CLASS_TEXT
    if (value.equals("password") or value.equals("password_confirmation"))
      editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
  }

  fun getInputType() : String? {
    return inputType
  }

  fun setErrorText(value: String?) {
    textError.text = value
  }

  fun showErrorText() {
    textError.visibility = TextView.VISIBLE
  }

  fun hideErrorText() {
    textError.visibility = TextView.GONE
  }

  fun validate() : Boolean {
    val type = getInputType()
    val text = getInputText()
    var valid = true

    if (type.equals("text"))
      if (text.isEmpty() || !text.matches("[a-zA-Z ]+".toRegex())) {
        editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        textError?.visibility = TextView.VISIBLE

        valid = false
      }

    if (type.equals("email"))
      if (text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
        editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        textError?.visibility = TextView.VISIBLE

        valid = false
      }

    if (type.equals("password"))
      if (text.length < MIN_PASSWORD_LENGTH) {
        editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        textError?.visibility = TextView.VISIBLE

        valid = false
      }

    if (!valid)
      return false

    editText?.background?.mutate()?.setColorFilter(colorPrimaryDark!!, PorterDuff.Mode.SRC_ATOP)
    textError?.visibility = TextView.GONE
    return true
  }
}