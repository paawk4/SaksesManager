package com.pawka.trellocloneapp.domain.user.use_cases

import com.google.firebase.auth.FirebaseUser
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditUserDataUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun editUserData(name: String, login: String, password: String){
        return userRepository.editUserData(name, login, password)
    }
}