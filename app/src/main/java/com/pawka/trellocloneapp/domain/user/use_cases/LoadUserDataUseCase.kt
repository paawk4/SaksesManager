package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class LoadUserDataUseCase(private val userRepository: UserRepository) {

    fun loadUserData(): User {
        return userRepository.loadUserData()
    }
}