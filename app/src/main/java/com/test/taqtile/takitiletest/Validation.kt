package com.test.taqtile.takitiletest

import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.widget.*


class Validation {
  val MIN_PASSWORD_LENGTH = 4

  fun validateNameEmailAndPassword (editTextName: EditText?, editTextEmail: EditText?, editTextPassword: EditText?, editTextPasswordConfirm: EditText?,
                                    textErrorName: TextView?, textErrorEmail: TextView?, textErrorPassword: TextView?, textErrorPasswordConfirm: TextView?,
                                    name: String?, email: String?, password: String?, passwordConfirm: String?) : Boolean {
    val validName = validateName(editTextName, textErrorName, name)
    val validEmail = validateEmail(editTextEmail, textErrorEmail, email)
    val validPassword = validatePassword(editTextPassword, editTextPasswordConfirm, textErrorPassword, textErrorPasswordConfirm, password, passwordConfirm)

    return (validName && validEmail && validPassword)
  }

  fun validateEmailAndPassword (editTextEmail: EditText?, editTextPassword: EditText?,
                textErrorEmail: TextView?, textErrorPassword: TextView?,
                email: String?, password: String?) : Boolean {
    val validEmail = validateEmail(editTextEmail, textErrorEmail, email)
    val validPassword = validatePassword(editTextPassword, textErrorPassword, password)

    return (validEmail && validPassword)
  }
  
  fun validateNameAndEmail (editTextName: EditText?, editTextEmail: EditText?,
                            textErrorName: TextView?, textErrorEmail: TextView?,
                            name: String?, email: String?) : Boolean {
    val validName = validateName(editTextName, textErrorName, name)
    val validEmail = validateEmail(editTextEmail, textErrorEmail, email)

    return (validName && validEmail)
  }

  private fun validateName (editText: EditText?, textError: TextView?, name: String?) : Boolean {
    if (name?.isEmpty()!! || !name.matches("\\D+".toRegex())) {
      editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textError?.visibility = TextView.VISIBLE

      return false
    }
    editText?.background?.mutate()?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
    textError?.visibility = TextView.GONE

    return true
  }

  private fun validateEmail (editText: EditText?, textError: TextView?, email: String?) : Boolean {
    Log.d("D", "validation: ")
    if (email?.isEmpty()!! || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      Log.d("D", "validation: ")
      editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textError?.visibility = TextView.VISIBLE

      return false
    }
    editText?.background?.mutate()?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
    textError?.visibility = TextView.GONE

    return true
  }

  private fun validatePassword (editText: EditText?, editTextConfirm: EditText?,
                                textError: TextView?, textErrorConfirm: TextView?,
                                password: String?, passwordConfirm: String?) : Boolean {
    if (password?.length!! < MIN_PASSWORD_LENGTH) {
      editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textError?.visibility = TextView.VISIBLE

      return false
    }
    editText?.background?.mutate()?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
    textError?.visibility = TextView.GONE

    if (!password.equals(passwordConfirm)) {
      editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      editTextConfirm?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textErrorConfirm?.visibility = TextView.VISIBLE

      return false
    }
    editTextConfirm?.background?.mutate()?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
    textErrorConfirm?.visibility = TextView.GONE

    return true
  }

  private fun validatePassword (editText: EditText?, textError: TextView?, password: String?) : Boolean {
    if (password?.length!! < MIN_PASSWORD_LENGTH) {
      editText?.background?.mutate()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
      textError?.visibility = TextView.VISIBLE

      return false
    }
    editText?.background?.mutate()?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
    textError?.visibility = TextView.GONE

    return true
  }
}