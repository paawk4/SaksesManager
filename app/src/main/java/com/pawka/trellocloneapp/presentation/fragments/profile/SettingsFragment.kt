package com.pawka.trellocloneapp.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var emailTv: TextView
    private lateinit var nameTv: TextView
    private lateinit var photoIv: CircleImageView
    private lateinit var changePhotoBtn: CircleImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(APP_ACTIVITY)[SettingsViewModel::class.java]
        initFields(view)
        configureToolbar()

    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title = "Настройки"
        APP_ACTIVITY.toolbar.inflateMenu(R.menu.settings_action_menu)
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    override fun onResume() {
        super.onResume()
        outputDataToTheUi()

    }

    private fun outputDataToTheUi() {
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

    private fun initFields(view: View) {
        emailTv = view.findViewById(R.id.settings_email)
        nameTv = view.findViewById(R.id.settings_name)
        photoIv = view.findViewById(R.id.settings_user_photo)
        changePhotoBtn = view.findViewById(R.id.settings_change_photo)
    }

    override fun onDestroy() {
        super.onDestroy()
        APP_ACTIVITY.toolbar.menu.clear()
    }
}