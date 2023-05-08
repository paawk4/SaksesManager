package com.pawka.trellocloneapp.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.MainActivity

fun hideKeyboard() {
    com.mikepenz.materialdrawer.R.color.material_drawer_accent
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun parseString(string: String?): String {
    return string?.trim() ?: ""
}