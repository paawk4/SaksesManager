package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository

class SignUpUserUseCase(private val userRepository: UserRepository) {

    fun signUpUser(name: String, email: String, password: String, callback: (isSignUp: Boolean) -> Unit) {
        return userRepository.signUpUser(name, email, password, callback)
    }
}