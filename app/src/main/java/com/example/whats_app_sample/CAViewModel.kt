package com.example.whats_app_sample

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.whats_app_sample.data.COLLECTION_USER
import com.example.whats_app_sample.data.UserData
import com.example.whats_app_sample.utils.ExceptionHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CAViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    val inProgress = exceptionHandler.inProgress
    val popupNotification = exceptionHandler.popupNotification
    private val signedIn = mutableStateOf(false)

//    init {
//        exceptionHandler.handleException(exception = null, "Test")
//    }

    fun onSignup(name: String, number: String, email: String, password: String) {
        if (name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()) {
            exceptionHandler.handleException(
                customMessage = "Please fill in all fields",
            )
            return
        }

        inProgress.value = true

        db.collection(COLLECTION_USER).whereEqualTo("number", number).get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                // Create User profile
                                createOrUpdateProfile(name = name, number = number)
                            } else {
                                exceptionHandler.handleException(
                                    task.exception, "Signup failed",
                                )
                            }
                        }
                } else {
                    exceptionHandler.handleException(
                        customMessage = "number already exists",
                    )
                }
                inProgress.value = false
            }
            .addOnFailureListener {
                exceptionHandler.handleException(
                    it, "",
                )
            }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageUrl: String? = null,
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(userId = uid, name = name, number = number, imageUrl = imageUrl)

        uid?.let { uid ->
            inProgress.value = true
            db.collection(COLLECTION_USER).document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        // Update User
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                inProgress.value = false
                            }
                            .addOnFailureListener {
                                exceptionHandler.handleException(it, "Cannot update user")
                            }
                    } else {
                        // Create new User
                        db.collection(COLLECTION_USER).document(uid).set(userData)
                        inProgress.value = false
                    }
                }
                .addOnFailureListener {
                    exceptionHandler.handleException(it, "Cannot retrieve user")
                }
        }
    }
}
