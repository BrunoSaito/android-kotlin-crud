package widgets.components

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.test.taqtile.takitiletest.OnboardApplication
import com.test.taqtile.takitiletest.R

class CustomSnackBarBuilder(context: Context?) {

  interface Listener {
    fun snackBarActionClick()
    fun onDismissed()
  }

  private var text: String = ""
  private var listener: Listener? = null
  private var view: View? = null
  private var actionText: String? = null
  private var actionColor: Int? = null
  private var callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null
  private var isDismissed: Boolean = false

  private val context: Context

  init {
    if (context != null) {
      this.context = context
    } else {
      this.context = OnboardApplication().getContext()
    }
  }

  fun withText(text: String?): CustomSnackBarBuilder {
    this.text = text ?: context.resources.getString(R.string.error_default_message)
    return this
  }

  fun withText(textId: Int): CustomSnackBarBuilder {
    this.text = context.getString(textId)
    return this
  }

  fun withListener(listener: Listener?): CustomSnackBarBuilder {
    this.listener = listener
    return this
  }

  fun withActionText(actionText: String?): CustomSnackBarBuilder {
    this.actionText = actionText?.toUpperCase()
    return this
  }

  fun withActionColor(colorInt: Int): CustomSnackBarBuilder {
    this.actionColor = colorInt
    return this
  }

  fun fromView(view: View): CustomSnackBarBuilder {
    this.view = view
    return this
  }

  fun withCallback(callback: BaseTransientBottomBar.BaseCallback<Snackbar>): CustomSnackBarBuilder {
    this.callback = callback
    return this
  }

  fun withNoConnectionStyle(): CustomSnackBarBuilder {
    text = context.resources.getString(R.string.error_connection_message)
    return this
  }

  fun withDefaultStyle(): CustomSnackBarBuilder {
    text = context.resources.getString(R.string.error_default_message)
    return this
  }

  fun show() {
    val view = if (this.view == null) (context as Activity).findViewById(android.R.id.content) else this.view as View

    // The average people reads 7 words in 2 seconds - from: http://www.execuread.com/facts/
    val duration = if (text.trim().split(" ").count() > 7) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT

    val snackBar = Snackbar.make(view, text, duration)

    if (actionText != null) {
      snackBar.setAction(actionText, {
        listener?.snackBarActionClick()
      })
      snackBar.duration = Snackbar.LENGTH_LONG
      snackBar.setActionTextColor(if (actionColor != null) ContextCompat.getColor(context, actionColor as Int) else ContextCompat.getColor(context, R.color.color_primary))
    }

    val sbView = snackBar.view
    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_snack_bar_background))
    (sbView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(context, R.color.color_snack_bar_text))

    if (callback != null) {
      snackBar.addCallback(callback as BaseTransientBottomBar.BaseCallback<Snackbar>)
    } else {
      snackBar.addCallback( object : Snackbar.Callback() {

        override fun onShown(snackBar: Snackbar?) {
          super.onShown(snackBar)
          isDismissed = false
        }

        override fun onDismissed(snackBar: Snackbar?, event: Int) {
          super.onDismissed(snackBar, event)
          if (!isDismissed) {
            listener?.onDismissed()
          }
        }
      })
    }

    snackBar.show()
  }
}