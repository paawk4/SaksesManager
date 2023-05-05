package com.pawka.trellocloneapp.presentation.app_drawer

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.presentation.fragments.sign_up.SignUpViewModel
import com.pawka.trellocloneapp.utils.Constants
import com.pawka.trellocloneapp.utils.Constants.APP_ACTIVITY

class AppDrawer {

    private lateinit var viewModel: AppDrawerViewModel


    private var currentUser = User()

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mCurrentProfile: ProfileDrawerItem

    fun create() {
        initLoader()
        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
        viewModel = ViewModelProvider(APP_ACTIVITY)[AppDrawerViewModel::class.java]
    }

    fun disableDrawer() {
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.toolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
//            hideKeyboard()
        }
    }

    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.toolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()
        }
        viewModel.currentUserData.observe(APP_ACTIVITY) {
            if (it.id != "")
                currentUser = it
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withToolbar(APP_ACTIVITY.toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Профиль")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_nav_user),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Выход")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_nav_sign_out)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    clickToItem(position)
                    return false
                }
            }).build()
    }

    private fun clickToItem(position: Int) {
//        when (position) {
//            1 ->
//            2 ->
//        }
    }

    private fun createHeader() {
        mCurrentProfile = ProfileDrawerItem()
            .withName(currentUser.name)
            .withEmail(currentUser.email)
            .withIcon(currentUser.image)
            .withIdentifier(200)
        mHeader = AccountHeaderBuilder()
            .withActivity(APP_ACTIVITY)
            .withHeaderBackground(R.color.colorAccent)
            .addProfiles(
                mCurrentProfile
            ).build()
    }

    fun updateHeader() {
//        mCurrentProfile
//            .withName(USER.fullname)
//            .withEmail(USER.phone)
//            .withIcon(USER.photoUrl)
//        mHeader.updateProfile(mCurrentProfile)
    }

    private fun initLoader() {
//        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
//            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
//                imageView.downloadAndSetImage(uri.toString())
//            }
//        })
    }
}