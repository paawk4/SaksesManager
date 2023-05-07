package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class GetCurrentUserDataUseCase(private val userRepository: UserRepository) {
    fun getCurrentUserData(): MutableLiveData<User?> {
        return userRepository.getCurrentUserData()
    }
}