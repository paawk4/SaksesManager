package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCurrentFirebaseUserUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getCurrentFirebaseUser(): FirebaseUser? {
        return userRepository.getCurrentFirebaseUser()
    }
}