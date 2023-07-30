package com.pawka.trellocloneapp.presentation.fragments.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.use_cases.SignInUserUseCase
import com.pawka.trellocloneapp.utils.parseString

class SignInViewModel(private val signInUserUseCase : SignInUserUseCase) : ViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    fun signInUser(inputEmail: String, inputPassword: String, callback: (isSignIn: Boolean) -> Unit) {
        val email = parseString(inputEmail)
        val password = parseString(inputPassword)
        if (validateInput(email, password)) {
            signInUserUseCase.signInUser(email, password, callback)
        } else {
            callback(false)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var result = true
        if (email.isBlank()) {
            _errorInputEmail.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

}