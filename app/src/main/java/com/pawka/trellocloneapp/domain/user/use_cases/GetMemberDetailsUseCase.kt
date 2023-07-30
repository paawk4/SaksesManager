package com.pawka.trellocloneapp.domain.user.use_cases

import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMemberDetailsUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getMemberDetails(email: String, callback: (User) -> Unit) {
        return userRepository.getMemberDetails(email, callback)
    }
}