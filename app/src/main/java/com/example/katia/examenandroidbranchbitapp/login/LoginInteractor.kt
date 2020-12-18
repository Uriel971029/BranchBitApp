package com.example.katia.examenandroidbranchbitapp.login

import android.util.Log
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.registro.RegistroInteractor
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class LoginInteractor {

    private val TAG = "firebase_login"
    private lateinit var mListener: OnResponseLoginInteractor


    interface OnResponseLoginInteractor : ResponseInterface {
        fun onInteractorLoginExitoso()
        fun onInteractorLoginError()
    }


    fun login(auth: FirebaseAuth, correo: String, contrasenia: String, listener: OnResponseLoginInteractor){
        mListener = listener
            auth.signInWithEmailAndPassword(
                correo,
                contrasenia
            ).addOnCompleteListener({
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
                    mListener.onInteractorLoginExitoso()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    //Utils.showMessage(mView, getString(R.string.login_error))
                    mListener.onInteractorLoginError()
                }
            })
    }
}