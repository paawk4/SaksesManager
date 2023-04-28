package com.pawka.trellocloneapp.domain.user

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun signUpUser(login: String, password: String): FirebaseUser?

    fun signInUser(email: String, password: String): User

    fun loadUserData(): User

    fun getCurrentUserId(): String
}