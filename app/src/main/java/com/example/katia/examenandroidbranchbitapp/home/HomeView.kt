package com.example.katia.examenandroidbranchbitapp.home

import com.example.katia.examenandroidbranchbitapp.ViewInterface
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

interface HomeView : ViewInterface {

    fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?)
    fun onObtenerColaboresError()
}