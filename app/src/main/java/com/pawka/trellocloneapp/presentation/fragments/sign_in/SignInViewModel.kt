package com.pawka.trellocloneapp.presentation.fragments.sign_in

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignInUserUseCase

class SignInViewModel : ViewModel() {

    private val repository = UserRepositoryImpl

    private val signInUserUseCase = SignInUserUseCase(repository)
    private val getCurrentUserIdUseCase = GetCurrentUserIdUseCase(repository)

    val currentFirebaseUid = getCurrentUserIdUseCase.getCurrentUserId()

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    fun signInUser(inputEmail: String, inputPassword: String) {
        val email = parseString(inputEmail)
        val password = parseString(inputPassword)
        if (validateInput(email, password)) {
            signInUserUseCase.signInUser(email, password)
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

    private fun parseString(string: String?): String {
        return string?.trim() ?: ""
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

}