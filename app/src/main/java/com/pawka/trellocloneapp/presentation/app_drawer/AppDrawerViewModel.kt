package com.pawka.trellocloneapp.presentation.app_drawer

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase

class AppDrawerViewModel: ViewModel() {
    private val repository = UserRepositoryImpl

    private val getCurrentUserDataUseCase = GetCurrentUserDataUseCase(repository)

    val currentUserData = getCurrentUserDataUseCase.getCurrentUserData()
}