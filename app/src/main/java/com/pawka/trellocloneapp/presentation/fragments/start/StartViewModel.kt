package com.pawka.trellocloneapp.presentation.fragments.start

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentFirebaseUserUseCase

class StartViewModel(private val getCurrentFirebaseUserUseCase: GetCurrentFirebaseUserUseCase) :
    ViewModel() {

    private val currentUser = getCurrentFirebaseUserUseCase.getCurrentFirebaseUser()

    fun isAuthorizedUser(): Boolean = currentUser != null
}