package com.pawka.trellocloneapp.presentation.fragments.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SetProfileImageUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignOutUserUseCase

class SettingsViewModel(
    private val getCurrentUserDataUseCase: GetCurrentUserDataUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val setProfileImageUseCase: SetProfileImageUseCase,
) : ViewModel() {

    val currentUserLiveData = getCurrentUserDataUseCase.getCurrentUserData()

    fun signOut() {
        signOutUserUseCase.signOutUser()
    }

    fun putImageToStorage(uri: Uri, callback: () -> Unit) {
        setProfileImageUseCase.setProfileImage(uri, callback)
    }
}