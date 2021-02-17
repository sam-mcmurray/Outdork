package dev.samuelmcmurray.data.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "LoginRepository"
class LoginRepository {
    private var application: Application
    private var firebaseAuth: FirebaseAuth
    var userLiveData: MutableLiveData<FirebaseUser>
    var loggedOutLiveData: MutableLiveData<Boolean>

    constructor(application: Application) {
        this.application = application
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userLiveData = MutableLiveData()
        this.loggedOutLiveData = MutableLiveData()

        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser);
            loggedOutLiveData.postValue(false);
        }
    }


    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                ContextCompat.getMainExecutor(application),
                { task ->
                    Log.d(TAG, "onComplete: ")
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        userLiveData.postValue(firebaseUser)
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Login Failure: " + (task.exception?.message
                                ?: "failed"),
                            Toast.LENGTH_SHORT
                        ).show()
                        loggedOutLiveData.postValue(true)
                    }
                })
    }

}
