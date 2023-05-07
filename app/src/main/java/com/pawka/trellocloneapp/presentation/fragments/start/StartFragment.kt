package com.pawka.trellocloneapp.presentation.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.app_drawer.AppDrawerViewModel
import com.pawka.trellocloneapp.utils.Constants
import com.pawka.trellocloneapp.utils.Constants.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.Constants.NAV_CONTROLLER

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

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
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]
    }
}