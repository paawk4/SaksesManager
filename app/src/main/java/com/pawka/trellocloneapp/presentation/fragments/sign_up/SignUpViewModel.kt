package com.pawka.trellocloneapp.presentation.fragments.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignUpUserUseCase

class SignUpViewModel : ViewModel() {

    private val repository = UserRepositoryImpl

    private val signUpUserUseCase = SignUpUserUseCase(repository)
    private val getCurrentUserIdUseCase = GetCurrentUserIdUseCase(repository)

    val currentFirebaseUid = getCurrentUserIdUseCase.getCurrentUserId()

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    fun signUpUser(inputName: String,inputEmail: String, inputPassword: String) {
        val name = parseString(inputName)
        val email = parseString(inputEmail)
        val password = parseString(inputPassword)

        if (validateInput(name, email, password)) {
            signUpUserUseCase.signUpUser(name, email, password)
        }
    }

    private fun validateInput(name:String, email: String, password: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
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

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

}