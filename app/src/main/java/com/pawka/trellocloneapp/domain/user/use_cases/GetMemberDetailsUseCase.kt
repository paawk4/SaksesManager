package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class GetMemberDetailsUseCase(private val repository: UserRepository) {

    fun getMemberDetails(email: String, callback: (User) -> Unit) {
        return repository.getMemberDetails(email, callback)
    }
}