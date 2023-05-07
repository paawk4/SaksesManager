package com.pawka.trellocloneapp.domain.user.use_cases

import com.google.firebase.auth.FirebaseUser
import com.pawka.trellocloneapp.domain.user.UserRepository

class EditUserDataUseCase(private val userRepository: UserRepository) {

    fun editUserData(name: String, login: String, password: String){
        return userRepository.editUserData(name, login, password)
    }
}