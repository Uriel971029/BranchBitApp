package com.example.katia.examenandroidbranchbitapp

interface ResponseInterface {
    fun onResponseErrorServidor()
    fun onResponseSinConexion()
    fun onResponseTiempoEsperadoAgotado()
}