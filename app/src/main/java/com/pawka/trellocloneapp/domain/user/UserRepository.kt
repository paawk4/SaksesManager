package com.pawka.trellocloneapp.domain.user

import androidx.lifecycle.LiveData

interface UserRepository {

    fun signUpUser(name: String, login: String, password: String)

    fun signInUser(email: String, password: String)

    fun getCurrentUserId(): LiveData<String>
    fun getCurrentUserData(): LiveData<User>
}