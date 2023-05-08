package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository

class GetCurrentUserIdUseCase(private val repository: UserRepository) {

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }
}