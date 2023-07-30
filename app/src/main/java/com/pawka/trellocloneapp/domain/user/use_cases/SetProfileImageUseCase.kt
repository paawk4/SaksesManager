package com.pawka.trellocloneapp.domain.user.use_cases

import android.net.Uri
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetProfileImageUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun setProfileImage(uri: Uri, callback: () -> Unit) {
        return userRepository.setProfileImage(uri, callback)
    }
}