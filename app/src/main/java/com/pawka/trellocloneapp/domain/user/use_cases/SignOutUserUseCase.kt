package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.UserRepository

class SignOutUserUseCase(private val repository: UserRepository) {

    fun signOutUser() {
        return repository.signOutUser()
    }
}