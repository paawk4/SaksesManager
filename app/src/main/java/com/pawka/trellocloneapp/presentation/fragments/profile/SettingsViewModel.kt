package com.pawka.trellocloneapp.presentation.fragments.profile

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase

class SettingsViewModel: ViewModel() {

    private val repository = UserRepositoryImpl

    private val getCurrentUserDataUseCase = GetCurrentUserDataUseCase(repository)

    val currentUserLiveData = getCurrentUserDataUseCase.getCurrentUserData()
}