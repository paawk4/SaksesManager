package com.pawka.trellocloneapp.presentation.fragments.start

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : BaseFragment(R.layout.fragment_start) {

    private val viewModel by viewModel<StartViewModel>()

    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NAV_CONTROLLER = findNavController()
        initViews(view)
        configureToolbar()

        signInBtn.setOnClickListener {
            signIn()
        }
        signUpBtn.setOnClickListener {
            signUp()
        }

        if (viewModel.isAuthorizedUser()) {
            NAV_CONTROLLER.navigate(R.id.action_startFragment_to_boardsFragment)
        }
    }

    private fun signUp() {
        NAV_CONTROLLER.navigate(R.id.action_startFragment_to_signUpFragment)
    }

    private fun signIn() {
        NAV_CONTROLLER.navigate(R.id.action_startFragment_to_signInFragment)
    }

    private fun initViews(view: View) {
        signInBtn = view.findViewById(R.id.sign_in_btn)
        signUpBtn = view.findViewById(R.id.sign_up_btn)
    }
}