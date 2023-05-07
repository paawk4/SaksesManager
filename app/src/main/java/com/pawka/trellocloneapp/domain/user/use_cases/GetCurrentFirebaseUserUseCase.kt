package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class GetCurrentFirebaseUserUseCase(private val userRepository: UserRepository) {

    fun getCurrentFirebaseUser(): FirebaseUser? {
        return userRepository.getCurrentFirebaseUser()
    }
}