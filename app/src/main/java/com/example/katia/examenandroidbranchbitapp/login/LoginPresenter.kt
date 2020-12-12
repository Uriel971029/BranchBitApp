package com.example.katia.examenandroidbranchbitapp.login

import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(var view: LoginView, val interactor: LoginInteractor) {

    fun login(correo: String, contrasenia: String, auth: FirebaseAuth) {

        if (view != null) {
            view.showLoader()
            interactor.login(
                auth,
                correo,
                contrasenia,
                object : LoginInteractor.OnResponseLoginInteractor {
                    override fun onInteractorLoginExitoso() {
                        view.hideLoader()
                        view.onLoginExitoso()
                    }

                    override fun onInteractorLoginError() {
                        view.hideLoader()
                        view.onLoginError()
                    }

                    override fun onResponseErrorServidor() {
                        view.hideLoader()
                        view.onLoginError()
                    }

                    override fun onResponseSinConexion() {
                        view.hideLoader()
                        view.onLoginError()
                    }

                    override fun onResponseTiempoEsperadoAgotado() {
                        view.hideLoader()
                        view.onLoginError()
                    }

                })
        }
    }
}