package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class SignInUserUseCase(private val userRepository: UserRepository) {

    fun signInUser(email: String, password: String) {
        return userRepository.signInUser(email, password)
    }
}