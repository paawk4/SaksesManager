package com.pawka.trellocloneapp.domain.user.use_cases

import com.google.firebase.auth.FirebaseUser
import com.pawka.trellocloneapp.domain.user.UserRepository

class SignUpUserUseCase(private val userRepository: UserRepository) {

    fun signUpUser(email: String, password: String): FirebaseUser? {
        return userRepository.signUpUser(email, password)
    }
}