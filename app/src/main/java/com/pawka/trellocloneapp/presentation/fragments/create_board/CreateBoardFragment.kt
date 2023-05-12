package com.pawka.trellocloneapp.presentation.fragments.create_board

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import com.pawka.trellocloneapp.utils.PICK_IMAGE_REQUEST_CODE
import com.pawka.trellocloneapp.utils.READ_STORAGE_PERMISSION_CODE
import com.pawka.trellocloneapp.utils.showImageChooser
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException

class CreateBoardFragment : BaseFragment(R.layout.fragment_create_board) {

    private lateinit var viewModel: CreateBoardViewModel

    private lateinit var createBoardBtn: Button
    private lateinit var nameBoardTil: TextInputLayout
    private lateinit var nameBoardEt: TextInputEditText
    private lateinit var imageBoardIv: CircleImageView
    private var selectedImageFileUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateBoardViewModel::class.java]

        configureToolbar()
        initViews(view)
        addTextChangeListener()
        observeViewModel()

        createBoardBtn.setOnClickListener {
            viewModel.createBoard(nameBoardEt.text.toString()) {
                NAV_CONTROLLER.popBackStack()
            }
        }
        imageBoardIv.setOnClickListener {
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
    }

    private fun addTextChangeListener() {
        nameBoardEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            nameBoardTil.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }
    }

    private fun initViews(view: View) {
        createBoardBtn = view.findViewById(R.id.create_board_create_btn)
        nameBoardEt = view.findViewById(R.id.create_board_name_et)
        imageBoardIv = view.findViewById(R.id.create_board_image)
        nameBoardTil = view.findViewById(R.id.create_board_name_til)
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title = "Создать доску"
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            selectedImageFileUri = data.data
            uploadBoardImage()
            try {
                Glide.with(this)
                    .load(selectedImageFileUri)
                    .fitCenter()
                    .placeholder(R.drawable.ic_board_place_holder)
                    .into(imageBoardIv)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadBoardImage() {
        showProgressDialog()

        if (selectedImageFileUri != null) {
            viewModel.putImageToStorage(selectedImageFileUri!!) {
                hideProgressDialog()
            }
        }
    }
}