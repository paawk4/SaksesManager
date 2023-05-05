package com.pawka.trellocloneapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

object UserRepositoryImpl : UserRepository {

    private val currentUserIdLiveData = MutableLiveData<String>()

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
                    currentUserIdLiveData.value = ""
                }
            }
    }

    override fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                currentUserIdLiveData.value = auth.currentUser?.uid ?: ""
            }
            .addOnFailureListener {
                currentUserIdLiveData.value = ""
                Log.d("Login", "Login Failed\n")
            }
    }

    override fun getCurrentUserId(): LiveData<String> {
        return currentUserIdLiveData
    }

    class UserFirebaseHandler {

        private val db = FirebaseDatabase.getInstance(DB_URL).reference.child(USERS)
        fun writeUserToDatabase(userInfo: User) {
            val user = mutableMapOf<String, Any>()
            user["id"] = userInfo.id
            user["name"] = userInfo.name
            user["email"] = userInfo.email
            db.child(userInfo.id).setValue(user)
                .addOnSuccessListener {
                    currentUserIdLiveData.value = auth.currentUser?.uid ?: ""
                }
                .addOnFailureListener {
                    Log.d("Register", "writeUserToDatabase failed\n ${it.message}")
                }
        }

//        fun loadUserData(): User {
//            return db.collection(Constants.USERS)
//                .document(getCurrentUserId())
//                .get()
//                .result.toObject(User::class.java)!!
//        }
    }
}