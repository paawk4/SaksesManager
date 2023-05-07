package com.pawka.trellocloneapp.presentation.fragments.start

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentFirebaseUserUseCase

class StartViewModel: ViewModel() {

    private val repository = UserRepositoryImpl

    private val getCurrentFirebaseUserUseCase = GetCurrentFirebaseUserUseCase(repository)

    private val currentUser = getCurrentFirebaseUserUseCase.getCurrentFirebaseUser()

    fun isAuthorizedUser(): Boolean = currentUser != null
}