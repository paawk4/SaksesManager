package com.pawka.trellocloneapp.presentation.app_drawer

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignOutUserUseCase

class AppDrawerViewModel(
    private val getCurrentUserDataUseCase: GetCurrentUserDataUseCase,
    private val signOutUserUseCase: SignOutUserUseCase
) : ViewModel() {
    val currentUserData = getCurrentUserDataUseCase.getCurrentUserData()

    fun signOut() {
        signOutUserUseCase.signOutUser()
    }
}