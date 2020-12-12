package com.example.katia.examenandroidbranchbitapp.home

import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.request.requets.ColaboradoresRequest
import com.example.katia.examenandroidbranchbitapp.utils.ContextApplication

class HomeInteractor {

    private lateinit var mListener : OnHomeInteractorResponse

    interface OnHomeInteractorResponse : ResponseInterface {
        fun onInteractorObtenerColaboradores(employees : ArrayList<EmployeeDTO>?)
        fun onInteractorObtenerColaboradoresError()
    }

    fun getColaboradores(listener: OnHomeInteractorResponse){
        mListener = listener
        val request = ColaboradoresRequest(ContextApplication.getContextApplication())
        request.getColaboradores(

            object : ColaboradoresRequest.OnColaboradoresRequestResponse{
                override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {
                    mListener.onInteractorObtenerColaboradores(employees)
                }

                override fun onColaboradoresObtenidosError(message: String) {
                    TODO("Not yet implemented")
                }

                override fun onResponseErrorServidor() {
                    TODO("Not yet implemented")
                }

                override fun onResponseSinConexion() {
                    TODO("Not yet implemented")
                }

                override fun onResponseTiempoEsperadoAgotado() {
                    TODO("Not yet implemented")
                }

            }
        )
    }
}