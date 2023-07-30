package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInUserUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun signInUser(email: String, password: String, callback: (isSignIn: Boolean) -> Unit) {
        return userRepository.signInUser(email, password, callback)
    }
}