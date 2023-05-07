package com.pawka.trellocloneapp.presentation.fragments.sign_up

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
import com.pawka.trellocloneapp.utils.Constants
import com.pawka.trellocloneapp.utils.Constants.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.Constants.NAV_CONTROLLER

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel

    private lateinit var layoutView: View

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        observeViewModel()
        configureToolbar()

        btnSignUp.setOnClickListener {
            viewModel.signUpUser(
                etName.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.visibility = View.VISIBLE
        APP_ACTIVITY.toolbar.title = "Регистрация"
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            tilName.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }
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
        viewModel.currentUserData.observe(viewLifecycleOwner) {
            if (it != null && it.id != "error") {
                Toast.makeText(layoutView.context, "Регистрация пройдена успешно", Toast.LENGTH_SHORT)
                    .show()
                NAV_CONTROLLER.navigate(R.id.action_signUpFragment_to_boardsFragment)
            }
            if (it?.id == "error") {
                Toast.makeText(layoutView.context, "Попробуй еще раз", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
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

    private fun initViews(view: View) {
        layoutView = view
        tilName = view.findViewById(R.id.sign_up_name_til)
        tilEmail = view.findViewById(R.id.sign_up_email_til)
        tilPassword = view.findViewById(R.id.sign_up_password_til)

        etName = view.findViewById(R.id.sign_up_name_et)
        etEmail = view.findViewById(R.id.sign_up_email_et)
        etPassword = view.findViewById(R.id.sign_up_password_et)

        btnSignUp = view.findViewById(R.id.sign_up_page_btn)
    }
}