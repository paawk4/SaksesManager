package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpUserUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun signUpUser(name: String, email: String, password: String, callback: (isSignUp: Boolean) -> Unit) {
        return userRepository.signUpUser(name, email, password, callback)
    }
}