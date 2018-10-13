package widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.test.taqtile.takitiletest.R
import kotlinx.android.synthetic.main.submit_button.view.*


class SubmitButton (context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

  private var buttonText: String? = null
  private var listener: OnClickListener? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.submit_button, this, true)

    attrs.let {
      val typedArray = context.obtainStyledAttributes(it, R.styleable.SubmitButton, 0, 0)
      buttonText = resources.getText(typedArray
              .getResourceId(R.styleable.SubmitButton_button_text, R.string.default_submit)).toString()

      setButtonText(buttonText)

      typedArray.recycle()
    }
  }

  override fun dispatchTouchEvent(event: MotionEvent): Boolean {
    if (event.action == MotionEvent.ACTION_UP) {
      if (listener != null) listener?.onClick(this)
    }
    return super.dispatchTouchEvent(event)
  }

  override fun setOnClickListener(listener: View.OnClickListener?) {
    this.listener = listener
  }

  fun setButtonText(value: String?) {
    button.text = value
  }

  fun lockButton() {
    progressBar.visibility = ProgressBar.VISIBLE
    button.text = ""
    button.isEnabled = false
  }

  fun unlockButton() {
    progressBar.visibility = ProgressBar.GONE
    button.text = buttonText
    button.isEnabled = true
  }
}