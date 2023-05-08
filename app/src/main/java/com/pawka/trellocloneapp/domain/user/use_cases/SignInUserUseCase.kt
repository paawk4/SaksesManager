package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository

class SignInUserUseCase(private val userRepository: UserRepository) {

    fun signInUser(email: String, password: String, callback: (isSignIn: Boolean) -> Unit) {
        return userRepository.signInUser(email, password, callback)
    }
}