package com.pawka.trellocloneapp.domain.user

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun signUpUser(name: String, login: String, password: String, callback: (isSignUp: Boolean) -> Unit)

    fun signInUser(email: String, password: String, callback: (isSignIn: Boolean) -> Unit)

    fun signOutUser()

    fun getCurrentUserData(): MutableLiveData<User?>
    fun getCurrentUserId(): String?

    fun getCurrentFirebaseUser(): FirebaseUser?

    fun editUserData(name: String, login: String, password: String)
}