package com.example.katia.examenandroidbranchbitapp.registro

import android.util.Log
import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroInteractor {

    private val TAG = "firebase"
    private lateinit var mListener: OnRegistroInteractorResponse

    interface OnRegistroInteractorResponse : ResponseInterface {
        fun onInteractorRegistroExitoso()
        fun onInteractorRegistroError()
    }

    fun registrar(
        correo: String,
        contrasenia: String,
        auth: FirebaseAuth,
        listener: OnRegistroInteractorResponse
    ) {
        mListener = listener
        //Firebase registration
        auth.createUserWithEmailAndPassword(correo, contrasenia).addOnCompleteListener({
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                //create a backup in the cloud
                saveInCloud(correo, contrasenia)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", it.exception)
                mListener.onInteractorRegistroError()
            }
        })
    }

    private fun saveInCloud(correo: String, contrasenia: String) {
        val db = Firebase.firestore
        val user = hashMapOf(
            "correo" to correo,
            "contrasenia" to contrasenia,
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                mListener.onInteractorRegistroExitoso()
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                mListener.onInteractorRegistroError()
                Log.w(TAG, "Error adding document", e)
            }
    }
}