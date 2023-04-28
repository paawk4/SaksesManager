package com.pawka.trellocloneapp.data

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import learning.self.kotlin.projectmanager.utils.Constants

object UserRepositoryImpl : UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    override fun signUpUser(login: String, password: String): FirebaseUser? {
        var firebaseUser: FirebaseUser? = null
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        firebaseUser = it
                    }
                } else {
                    Log.d("Register", "Registration Failed. Try Again")
                }
            }
        return firebaseUser
    }

    override fun signInUser(email: String, password: String): User {
        var loggedInUser = User()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loggedInUser = loadUserData()
                } else {
                    Log.d("Register", "Authentication failed")
                }
            }
        return loggedInUser
    }

    override fun loadUserData(): User {
        var loggedInUser = User()
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                loggedInUser = document.toObject(User::class.java)!!
            }
        return loggedInUser
    }

    override fun getCurrentUserId(): String {
        return auth.currentUser!!.uid
    }
}