package com.example.katia.examenandroidbranchbitapp.home

import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.request.requets.ColaboradoresRequest
import com.example.katia.examenandroidbranchbitapp.utils.ContextApplication

class HomeInteractor {

    private lateinit var mListener: OnHomeInteractorResponse

    interface OnHomeInteractorResponse : ResponseInterface {
        fun onInteractorObtenerColaboradores(employees: ArrayList<EmployeeDTO>?)
        fun onInteractorObtenerColaboradoresError()
        fun onInteractorEmployeeAgregado()
    }

    fun getColaboradores(listener: OnHomeInteractorResponse) {
        mListener = listener
        val request = ColaboradoresRequest(ContextApplication.getContextApplication())
        request.getColaboradores(
            object : ColaboradoresRequest.OnColaboradoresRequestResponse {
                override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {
                    mListener.onInteractorObtenerColaboradores(employees)
                }

                override fun onColaboradoresObtenidosError(message: String) {
                    mListener.onInteractorObtenerColaboradoresError()
                }

                override fun onColaboradorAgregado() {
                }

                override fun onResponseErrorServidor() {
                    mListener.onInteractorObtenerColaboradoresError()
                }

                override fun onResponseSinConexion() {
                    mListener.onInteractorObtenerColaboradoresError()
                }

                override fun onResponseTiempoEsperadoAgotado() {
                    mListener.onInteractorObtenerColaboradoresError()
                }
            }
        )
    }

    fun addEmployee(employeeDTO: EmployeeDTO, listener: OnHomeInteractorResponse) {
        val request = ColaboradoresRequest(ContextApplication.getContextApplication())
        request.addEmployee(
            employeeDTO,
            object : ColaboradoresRequest.OnColaboradoresRequestResponse {

                override fun onColaboradorAgregado() {
                    mListener.onInteractorEmployeeAgregado()
                }

                override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {
                }

                override fun onColaboradoresObtenidosError(message: String) {
                }

                override fun onResponseErrorServidor() {
                }

                override fun onResponseSinConexion() {
                }

                override fun onResponseTiempoEsperadoAgotado() {
                }

            }
        )
    }
}