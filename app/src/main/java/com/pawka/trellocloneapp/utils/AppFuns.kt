package com.pawka.trellocloneapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pawka.trellocloneapp.presentation.MainActivity

fun hideKeyboard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
    hideKeyboard()
}

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun parseString(string: String?): String {
    return string?.trim() ?: ""
}

fun getFileExtension(activity: Activity, uri: Uri?): String? {
    return MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
}

fun showImageChooser(fragment: Fragment) {
    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    fragment.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
}