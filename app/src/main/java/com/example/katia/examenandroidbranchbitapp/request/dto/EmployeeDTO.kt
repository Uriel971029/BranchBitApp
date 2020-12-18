package com.example.katia.examenandroidbranchbitapp.request.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//private val ID_KEY = "id"
//private val LOCATION_KEY = "location"
//private val NAME_KEY = "name"
//private val MAIL_KEY = "mail"

//@Entity
data class EmployeeDTO(
//    @PrimaryKey
    @SerializedName("id") val id: Int,
//    @ColumnInfo
    @SerializedName("location") var location: CoordinatesDTO,
//    @ColumnInfo
    @SerializedName("name") var nombre: String,
//    @ColumnInfo
    @SerializedName("mail") var correo: String
) : Serializable

//class EmployeeDTO(data: JSONObject?) : Serializable {
//
//    var id: Int = 0
//    lateinit var nombre: String
//    lateinit var location: LatLng
//    lateinit var correo: String
//
//    init {
//        if (data != null) {
//            id = data.optInt(ID_KEY)
//            nombre = data.optString(NAME_KEY)
//            val jsonObject = data.optJSONObject(LOCATION_KEY)
//            val lat = jsonObject.optDouble("lat")
//            val log = jsonObject.optDouble("log")
//            location = LatLng(lat, log)
//            correo = data.optString(MAIL_KEY)
//
//        }
//    }
//}