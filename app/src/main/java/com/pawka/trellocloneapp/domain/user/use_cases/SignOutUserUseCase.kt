package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignOutUserUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun signOutUser() {
        return userRepository.signOutUser()
    }
}