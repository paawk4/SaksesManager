package com.pawka.trellocloneapp.presentation.fragments.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.restartActivity
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
        setHasOptionsMenu(true)
        initFields(view)
        configureToolbar()
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
                restartActivity()
            }

            R.id.settings_menu_change_name -> println()
        }
        return true
    }
}