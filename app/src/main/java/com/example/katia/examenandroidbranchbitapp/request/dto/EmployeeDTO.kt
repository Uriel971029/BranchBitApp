package com.example.katia.examenandroidbranchbitapp.request.dto

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import java.io.Serializable

private val ID_KEY = "id"
private val LOCATION_KEY = "location"
private val NAME_KEY = "name"
private val MAIL_KEY = "mail"


//data class EmployeeDTO(
//    @SerializedName("id") public val id: Int,
//    @SerializedName("location") var location: LatLng,
//    @SerializedName("name") val nombre: String,
//    @SerializedName("mail") val correo: String
//) : Serializable

class EmployeeDTO(data: JSONObject) : Serializable {

    var id: Int = 0
    lateinit var nombre: String
    lateinit var location: LatLng
    lateinit var correo: String

    init {
        id = data.optInt(ID_KEY)
        nombre = data.optString(NAME_KEY)
        val jsonObject = data.optJSONObject(LOCATION_KEY)
        val lat = jsonObject.optDouble("lat")
        val log = jsonObject.optDouble("log")
        location = LatLng(lat, log)
        correo = data.optString(MAIL_KEY)
    }


}