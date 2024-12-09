package com.example.tustracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StartPageViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // LiveData to hold the error message
    val errorMessage = MutableLiveData<String>()

    private val _journals = MutableLiveData<List<Journal>>()
    val journals: LiveData<List<Journal>> = _journals

    init {
        fetchJournalsFromFirebase()
    }

    // LiveData to hold the user's id
    val userId = MutableLiveData<String>()

    fun registerUser(kemail: String, password: String, function: () -> Unit) {
        // Check if the name, full name, or email field is empty
        if (kemail.isEmpty() || password.isEmpty()) {
            errorMessage.postValue("Name, email, or password cannot be empty")
            return
        }

        auth.createUserWithEmailAndPassword(kemail, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User is registered
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        // Post the user ID to a LiveData
                        userId.postValue(currentUser.uid)
                        function.invoke()
                    } else {
                        // Handle the case where currentUser is null
                        errorMessage.postValue("An error has occurred, Please try again later")
                    }
                } else {
                    // Handle failure
                    errorMessage.postValue(task.exception?.message)
                }
            }
    }


    fun loginUser(kemail: String, password: String, onLoginSuccess: () -> Unit) {
        // Check if the email or password field is empty
        if (kemail.isEmpty() || password.isEmpty()) {
            // Commented out to avoid displaying error messages in the ViewModel
            // loginErrorMessage.postValue("Email or password cannot be empty")
            return
        }

        auth.signInWithEmailAndPassword(kemail, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User is logged in
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        // Post the user ID to a LiveData
                        //loggedInUserId.postValue(currentUser.uid)

                        // Call the onLoginSuccess callback
                        onLoginSuccess.invoke()
                    } else {
                        // Handle the case where currentUser is null
                        // Commented out to avoid displaying error messages in the ViewModel
                        // loginErrorMessage.postValue("An error has occurred, Please try again later")
                    }
                } else {
                    // Handle login failure
                    // Commented out to avoid displaying error messages in the ViewModel
                    // loginErrorMessage.postValue(task.exception?.message)
                }
            }
    }

    private fun fetchJournalsFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val journalsRef =
            db.collection("journals") // Assuming journals are stored in this collection

        journalsRef.get().addOnSuccessListener { documents ->
            val journalList = mutableListOf<Journal>()
            for (document in documents) {
                val title = document.getString("title") ?: ""
                val content = document.getString("content") ?: ""
                val timestamp = document.getLong("timestamp") ?: 0L
                val userId = document.getString("userId") ?: ""

                journalList.add(Journal(title, content, timestamp, userId))
            }
            _journals.postValue(journalList)
        }.addOnFailureListener { exception ->
            // Handle failure here
        }
    }


    fun saveJournalToFirebase(
        title: String, content: String, onSuccess: () -> Unit, onFailure: (String) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val journalEntry = mapOf(
            "userId" to userId,
            "title" to title,
            "content" to content,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("journals").add(journalEntry).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception.message ?: "Unknown error")
            }
    }
}
