package com.pawka.trellocloneapp.domain.user.use_cases

import android.net.Uri
import com.pawka.trellocloneapp.domain.user.UserRepository

class SetProfileImageUseCase(private val repository: UserRepository) {

    fun setProfileImage(uri: Uri, callback: () -> Unit) {
        return repository.setProfileImage(uri, callback)
    }
}