package com.pawka.trellocloneapp.domain.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun signUpUser(name: String, login: String, password: String)

    fun signInUser(email: String, password: String)

    fun signOutUser()

    fun getCurrentUserData(): MutableLiveData<User?>

    fun getCurrentFirebaseUser(): FirebaseUser?

    fun editUserData(name: String, login: String, password: String)
}