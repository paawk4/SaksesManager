package com.pawka.trellocloneapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.pawka.trellocloneapp.R

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        initView(view)
        addTextChangeListeners()
        observeViewModel()

        btnSignIn.setOnClickListener {
            val user = viewModel.signInUser(etEmail.text.toString(), etPassword.text.toString())
            if (user?.fcmToken.isNullOrBlank()) {
                Toast.makeText(view.context, user?.email, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView(view: View) {
        tilEmail = view.findViewById(R.id.sign_in_email_til)
        tilPassword = view.findViewById(R.id.sign_in_password_til)
        etEmail = view.findViewById(R.id.sign_in_email_et)
        etPassword = view.findViewById(R.id.sign_in_password_et)
        btnSignIn = view.findViewById(R.id.sign_in_btn)
    }

    private fun addTextChangeListeners() {
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            tilEmail.error = if (it) {
                getString(R.string.error_input_email)
            } else {
                null
            }
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            tilPassword.error = if (it) {
                getString(R.string.error_input_password)
            } else {
                null
            }
        }
    }

}