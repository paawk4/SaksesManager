package com.pawka.trellocloneapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import com.pawka.trellocloneapp.utils.AppValueEventListener

object UserRepositoryImpl : UserRepository {

    private val currentUserLiveData = MutableLiveData<User?>()

    const val DB_URL =
        "https://trellocloneapp-b007f-default-rtdb.europe-west1.firebasedatabase.app/"
    const val USERS: String = "users"

    private val auth = FirebaseAuth.getInstance()

    private val userFirebaseHandler = UserFirebaseHandler()

    override fun signUpUser(name: String, login: String, password: String) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        val currentUser = User(it.uid, name, login)
                        userFirebaseHandler.writeUserToDatabase(currentUser)
                    }
                } else {
                    Log.d("Register", "Registration Failed\n")
                    currentUserLiveData.value = User(id = "error")
                }
            }
    }

    override fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userFirebaseHandler.loadUserData()
            }
            .addOnFailureListener {
                currentUserLiveData.value = User(id = "error")
                Log.d("Login", "Login Failed\n")
            }
    }

    override fun signOutUser() {
        auth.signOut()
        currentUserLiveData.value = null
    }

    override fun getCurrentUserData(): MutableLiveData<User?> {
        userFirebaseHandler.loadUserData()
        return currentUserLiveData
    }

    override fun getCurrentFirebaseUser(): FirebaseUser? {
        userFirebaseHandler.loadUserData()
        return auth.currentUser
    }

    override fun editUserData(name: String, login: String, password: String) {

    }

    class UserFirebaseHandler {

        private val db = FirebaseDatabase.getInstance(DB_URL).reference.child(USERS)
        fun writeUserToDatabase(userInfo: User) {
            db.child(userInfo.id).setValue(userInfo)
                .addOnSuccessListener {
                    loadUserData()
                }
                .addOnFailureListener {
                    Log.d("Register", "writeUserToDatabase failed\n ${it.message}")
                }
        }

        fun loadUserData() {
            auth.currentUser?.let {
                db.child(it.uid)
                    .addListenerForSingleValueEvent(AppValueEventListener {snapshot ->
                        val user = snapshot.getValue(User::class.java) ?: User()
                        currentUserLiveData.value = user
                    })
            }

        }
    }
}