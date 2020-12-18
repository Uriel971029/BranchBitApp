package com.example.katia.examenandroidbranchbitapp.home

import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

class HomePresenter(private var view: HomeView, private var interactor: HomeInteractor) :
    HomeInteractor.OnHomeInteractorResponse {

    fun getColaboradores() {
            view.showLoader()
            interactor.getColaboradores(this)

    }

    override fun onInteractorObtenerColaboradores(employees: ArrayList<EmployeeDTO>?) {
        view.hideLoader()
        view.onColaboradoresObtenidos(employees)
    }

    override fun onInteractorObtenerColaboradoresError() {
        view.hideLoader()
        view.onObtenerColaboresError()
    }

    override fun onResponseErrorServidor() {
        view.hideLoader()
        view.onObtenerColaboresError()
    }

    override fun onResponseSinConexion() {
        view.hideLoader()
        view.onObtenerColaboresError()
    }

    override fun onResponseTiempoEsperadoAgotado() {
        view!!.hideLoader()
        view.onObtenerColaboresError()
    }

}

