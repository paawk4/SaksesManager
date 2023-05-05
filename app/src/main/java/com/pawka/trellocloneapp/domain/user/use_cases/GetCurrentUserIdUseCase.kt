package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.LiveData
import com.pawka.trellocloneapp.domain.user.UserRepository

class GetCurrentUserIdUseCase(private val userRepository: UserRepository) {

    fun getCurrentUserId(): LiveData<String> {
        return userRepository.getCurrentUserId()
    }
}