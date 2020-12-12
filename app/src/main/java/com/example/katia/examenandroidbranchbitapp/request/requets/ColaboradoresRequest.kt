package com.example.katia.examenandroidbranchbitapp.request.requets

import android.content.Context
import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.request.RetrofitSingleton
import com.example.katia.examenandroidbranchbitapp.request.WebServices
import com.example.katia.examenandroidbranchbitapp.request.dto.DataDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.ResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColaboradoresRequest (context: Context) : Callback<ResponseDTO> {

    private val ID_KEY: String = "id"
    private val LOCATION_KEY: String = "location"
    private val NOMBRE_KEY: String = "name"
    private val MAIL_KEY: String = "mail"

    private lateinit var mListener : OnColaboradoresRequestResponse

    interface OnColaboradoresRequestResponse : ResponseInterface {
        fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?)
        fun onColaboradoresObtenidosError(message: String)
    }

    fun getColaboradores(listener: OnColaboradoresRequestResponse) {
        mListener = listener
        val getMethos = RetrofitSingleton.getInstance().create(WebServices.Methods.GetMethods::class.java)
        val request = getMethos.getDataEmployees()
        request.enqueue(this)
    }

    override fun onResponse(call: Call<ResponseDTO>?, response: Response<ResponseDTO>?) {
        var responseDTO : ResponseDTO = response?.body() as ResponseDTO
        unzipFile(responseDTO.data)
        var employeesData  = getDataFromJson()
        mListener.onColaboradoresObtenidos(employeesData)
    }

    private fun getDataFromJson() : ArrayList<EmployeeDTO>? {

        return null
    }

    private fun unzipFile(data: DataDTO) {

    }

    override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {
        mListener.onColaboradoresObtenidosError(t!!.message.toString())
    }


}