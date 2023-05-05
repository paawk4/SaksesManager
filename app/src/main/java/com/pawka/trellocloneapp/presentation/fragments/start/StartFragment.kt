package com.pawka.trellocloneapp.presentation.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.utils.Constants

class StartFragment : Fragment() {

    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    private fun configureToolbar() {
        Constants.APP_ACTIVITY.toolbar.visibility = View.GONE
        Constants.APP_ACTIVITY.toolbar.title = "S-STROY MANAGER"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        configureToolbar()


        signInBtn.setOnClickListener {
            signIn()
        }
        signUpBtn.setOnClickListener {
            signUp()
        }
        findNavController().navigate(R.id.action_startFragment_to_boardsFragment)
    }

    private fun signUp() {
        findNavController().navigate(R.id.action_startFragment_to_signUpFragment)
    }

    private fun signIn() {
        findNavController().navigate(R.id.action_startFragment_to_signInFragment)
    }

    private fun initViews(view: View) {
        signInBtn = view.findViewById(R.id.sign_in_btn)
        signUpBtn = view.findViewById(R.id.sign_up_btn)
    }
}