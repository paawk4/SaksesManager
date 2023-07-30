package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCurrentUserIdUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getCurrentUserId(): String? {
        return userRepository.getCurrentUserId()
    }
}