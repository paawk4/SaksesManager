package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository

class GetCurrentUserIdUseCase(private val userRepository: UserRepository) {

    fun getCurrentUserId(): String {
        return userRepository.getCurrentUserId()
    }
}