package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCurrentUserDataUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getCurrentUserData(): MutableLiveData<User?> {
        return userRepository.getCurrentUserData()
    }
}