package com.pawka.trellocloneapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pawka.trellocloneapp.R

class StartFragment : Fragment() {

    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        signInBtn.setOnClickListener {
            signIn()
        }
        signUpBtn.setOnClickListener {
            signUp()
        }
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