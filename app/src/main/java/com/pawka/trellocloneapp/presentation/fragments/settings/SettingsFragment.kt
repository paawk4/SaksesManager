package com.pawka.trellocloneapp.presentation.fragments.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.PICK_IMAGE_REQUEST_CODE
import com.pawka.trellocloneapp.utils.READ_STORAGE_PERMISSION_CODE
import com.pawka.trellocloneapp.utils.showImageChooser
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var emailTv: TextView
    private lateinit var nameTv: TextView
    private lateinit var photoIv: CircleImageView
    private lateinit var changePhotoBtn: CircleImageView

    private var selectedImageFileUri: Uri? = null
    private var profileImageURL: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(APP_ACTIVITY)[SettingsViewModel::class.java]
        setHasOptionsMenu(true)
        initFields(view)
        configureToolbar()
        changePhotoBtn.setOnClickListener {
            val permission = if (Build.VERSION.SDK_INT > 32) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            if (ContextCompat.checkSelfPermission(
                    APP_ACTIVITY,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    APP_ACTIVITY,
                    arrayOf(permission),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentUserLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                emailTv.text = it.email
                nameTv.text = it.name
                Glide
                    .with(APP_ACTIVITY)
                    .load(it.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(photoIv)
            }
        }
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title = "Настройки"
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    private fun initFields(view: View) {
        emailTv = view.findViewById(R.id.settings_email)
        nameTv = view.findViewById(R.id.settings_name)
        photoIv = view.findViewById(R.id.settings_user_photo)
        changePhotoBtn = view.findViewById(R.id.settings_change_photo)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                viewModel.signOut()
            }

            R.id.settings_menu_change_name -> println()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            selectedImageFileUri = data.data
            uploadUserImage()

            try {
                Glide.with(this)
                    .load(selectedImageFileUri)
                    .fitCenter()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(photoIv)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadUserImage() {
        showProgressDialog()

        if (selectedImageFileUri != null) {
            viewModel.putImageToStorage(selectedImageFileUri!!) {
                hideProgressDialog()
            }
        }
    }

}