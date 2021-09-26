package com.makazemi.cooking.ui

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.displayToast(@StringRes message: Int) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.displayToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun View.setVisibilityView(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}