package com.pawka.trellocloneapp.presentation.fragments.boards

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.utils.Constants.APP_ACTIVITY

class BoardsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
    }

    private fun configureToolbar() {
        APP_ACTIVITY.appDrawer.create()
        APP_ACTIVITY.appDrawer.enableDrawer()
        APP_ACTIVITY.toolbar.visibility = View.VISIBLE
        APP_ACTIVITY.toolbar.title = "S-STROY MANAGER"
        APP_ACTIVITY.toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        APP_ACTIVITY.toolbar.setTitleTextColor(Color.WHITE)

    }
}