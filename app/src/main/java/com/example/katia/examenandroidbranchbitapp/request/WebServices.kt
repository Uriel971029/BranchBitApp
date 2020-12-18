package com.example.katia.examenandroidbranchbitapp.request

import com.example.katia.examenandroidbranchbitapp.request.dto.ResponseDTO
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

class WebServices {
    companion object Methods {

        const val APP_JSON = "application/json"
        const val CONTENT_TYPE_JSON = "Content-Type: application/json"

        interface GetMethods {
            @GET("getFile.json?dl=0")
            fun getDataEmployees(): Call<ResponseDTO>
        }

        interface PostMethods {
        }

        fun createBody(params: HashMap<String, Any>): RequestBody {
            val paramsObject = JSONObject(params as Map<*, *>).toString()
            return RequestBody.create(MediaType.parse(APP_JSON), paramsObject)
        }
    }
}