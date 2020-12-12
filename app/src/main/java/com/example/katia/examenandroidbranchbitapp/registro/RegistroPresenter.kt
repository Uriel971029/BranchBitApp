package com.example.katia.examenandroidbranchbitapp.registro

import com.google.firebase.auth.FirebaseAuth

class RegistroPresenter(
    var mRegistroView: RegistroView?,
    val mRegistroInteractor: RegistroInteractor
) {

    fun registrarUsuario(correo: String, contrasenia: String, auth: FirebaseAuth) {
        if(mRegistroView != null){
            mRegistroView!!.showLoader()
            mRegistroInteractor.registrar(
                correo,
                contrasenia,
                auth,
                object : RegistroInteractor.OnRegistroInteractorResponse {
                    override fun onInteractorRegistroExitoso() {
                        mRegistroView!!.hideLoader()
                        mRegistroView?.onRegistroExitoso()
                    }

                    override fun onInteractorRegistroError() {
                        mRegistroView!!.hideLoader()
                        mRegistroView?.onRegistroError()
                    }

                    override fun onResponseErrorServidor() {
                        mRegistroView!!.hideLoader()
                        mRegistroView?.onRegistroError()
                    }

                    override fun onResponseSinConexion() {
                        mRegistroView!!.hideLoader()
                        mRegistroView?.onRegistroError()
                    }

                    override fun onResponseTiempoEsperadoAgotado() {
                        mRegistroView!!.hideLoader()
                        mRegistroView?.onRegistroError()
                    }
                }
            )
        }

    }

    fun destroy() {
        mRegistroView = null
    }
}